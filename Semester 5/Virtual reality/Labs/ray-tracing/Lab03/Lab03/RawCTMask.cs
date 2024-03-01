using System;
using System.IO;
using System.Text.RegularExpressions;

namespace rt;

public class RawCtMask: Geometry
{
    private readonly Vector _position;
    private readonly double _scale;
    private readonly ColorMap _colorMap;
    private readonly byte[] _data;

    private readonly int[] _resolution = new int[3];
    private readonly double[] _thickness = new double[3];
    private readonly Vector _v0;
    private readonly Vector _v1;

    public RawCtMask(string datFile, string rawFile, Vector position, double scale, ColorMap colorMap) : base(Color.NONE)
    {
        _position = position;
        _scale = scale;
        _colorMap = colorMap;

        var lines = File.ReadLines(datFile);
        foreach (var line in lines)
        {
            var kv = Regex.Replace(line, "[:\\t ]+", ":").Split(':');
            if (kv[0] == "Resolution")
            {
                _resolution[0] = Convert.ToInt32(kv[1]);
                _resolution[1] = Convert.ToInt32(kv[2]);
                _resolution[2] = Convert.ToInt32(kv[3]);
            } else if (kv[0] == "SliceThickness")
            {
                _thickness[0] = Convert.ToDouble(kv[1]);
                _thickness[1] = Convert.ToDouble(kv[2]);
                _thickness[2] = Convert.ToDouble(kv[3]);
            }
        }

        _v0 = position;
        _v1 = position + new Vector(_resolution[0]*_thickness[0]*scale, _resolution[1]*_thickness[1]*scale, _resolution[2]*_thickness[2]*scale);

        var len = _resolution[0] * _resolution[1] * _resolution[2];
        _data = new byte[len];
        using FileStream f = new FileStream(rawFile, FileMode.Open, FileAccess.Read);
        if (f.Read(_data, 0, len) != len)
        {
            throw new InvalidDataException($"Failed to read the {len}-byte raw data");
        }
    }
    
    private ushort Value(int x, int y, int z)
    {
        if (x < 0 || y < 0 || z < 0 || x >= _resolution[0] || y >= _resolution[1] || z >= _resolution[2])
        {
            return 0;
        }

        return _data[z * _resolution[1] * _resolution[0] + y * _resolution[0] + x];
    }
    
    public Tuple<double, double> GetAABB(Line line, double minDist, double maxDist)
    {
        Vector rayOrigin = line.X0;
        Vector rayDirection = line.Dx;

        var tStartX = (_v0.X - rayOrigin.X) / rayDirection.X;
        var tEndX = (_v1.X - rayOrigin.X) / rayDirection.X;
        
        var tStartY = (_v0.Y - rayOrigin.Y) / rayDirection.Y;
        var tEndY = (_v1.Y - rayOrigin.Y) / rayDirection.Y;
        
        var tStartZ = (_v0.Z - rayOrigin.Z) / rayDirection.Z;
        var tEndZ = (_v1.Z - rayOrigin.Z) / rayDirection.Z;

        if (1.0 / rayDirection.X < 0.0)
        {
            (tStartX, tEndX) = (tEndX, tStartX);
        }
        if (1.0 / rayDirection.Y < 0.0)
        {
            (tStartY, tEndY) = (tEndY, tStartY);
        }
        if (1.0 / rayDirection.Z < 0.0)
        {
            (tStartZ, tEndZ) = (tEndZ, tStartZ);
        }
        
        var t1 = Math.Max(minDist, Math.Max(tStartX, Math.Max(tStartY, tStartZ)));
        var t2 = Math.Min(maxDist, Math.Min(tEndX, Math.Min(tEndY, tEndZ)));
        
        return new Tuple<double, double>(t1, t2);
    }

    public override Intersection GetIntersection(Line line, double minDist, double maxDist)
    {
        // ADD CODE HERE

        var (tStart, tEnd) = GetAABB(line, minDist, maxDist);

        if (tStart > tEnd)
        {
            return Intersection.NONE;
        }

        var currentAlpha = 1d;
        var color = new Color();
        var normal = new Vector();
        var beenThere = false;
        var intersection = 0d;

        for (var t = tStart; t <= tEnd; t += _scale)
        {
            var point = line.CoordinateToPosition(t);
            var currentColor = GetColor(point);
            if (currentColor.Alpha == 0)
            {
                continue;
            }

            if (!beenThere)
            {
                intersection = t;
                normal = GetNormal(point);
                beenThere = true;
            }

            color += currentColor * currentColor.Alpha * currentAlpha;
            currentAlpha *= 1 - currentColor.Alpha;

            if (currentAlpha < 1e-10)
            {
                break;
            }
        }

        return new Intersection(true, beenThere, this, line, intersection, normal,
            Material.FromColor(color),
            color);
    }

    private int[] GetIndexes(Vector v)
    {
        return new []{
            (int)Math.Floor((v.X - _position.X) / _thickness[0] / _scale), 
            (int)Math.Floor((v.Y - _position.Y) / _thickness[1] / _scale),
            (int)Math.Floor((v.Z - _position.Z) / _thickness[2] / _scale)};
    }
    private Color GetColor(Vector v)
    {
        int[] idx = GetIndexes(v);

        ushort value = Value(idx[0], idx[1], idx[2]);
        return _colorMap.GetColor(value);
    }

    private Vector GetNormal(Vector v)
    {
        int[] idx = GetIndexes(v);
        double x0 = Value(idx[0] - 1, idx[1], idx[2]);
        double x1 = Value(idx[0] + 1, idx[1], idx[2]);
        double y0 = Value(idx[0], idx[1] - 1, idx[2]);
        double y1 = Value(idx[0], idx[1] + 1, idx[2]);
        double z0 = Value(idx[0], idx[1], idx[2] - 1);
        double z1 = Value(idx[0], idx[1], idx[2] + 1);

        return new Vector(x1 - x0, y1 - y0, z1 - z0).Normalize();
    }
}