using System;


namespace rt
{
    public class Ellipsoid : Geometry
    {
        private Vector Center { get; }
        private Vector SemiAxesLength { get; }
        private double Radius { get; }

        public Vector GetCenter()
        {
            return Center;
        }


        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Material material, Color color) : base(
            material, color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Color color) : base(color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }
        
        private Vector CalculateNormal(Vector center, double a, double b, double c, Vector point)
        {
            // Calculate the normal vector at the intersection point
            return new Vector(
                2 * (point.X - center.X) / (a * a),
                2 * (point.Y - center.Y) / (b * b),
                2 * (point.Z - center.Z) / (c * c)
            ).Normalize();
        }
        
        private Vector Linify(Vector v)
        {
            return new Vector(v.X / SemiAxesLength.X, v.Y / SemiAxesLength.Y, v.Z / SemiAxesLength.Z);
        }

        public Tuple<double?, double?> GetFirstLastIntersection(Line line)
        {
            var normalizedDirection  = Linify(line.Dx);
            var normalizedVectorToStart = Linify(line.X0 - Center);
            
            var a = normalizedDirection.Length2();
            var b = normalizedDirection * normalizedVectorToStart  * 2;
            var c = normalizedVectorToStart.Length2() - Radius * Radius;
            
            var delta = b * b - 4 * a * c;

            if (delta < 1e-10)
            {
                return new Tuple<double?, double?>(null, null);
            }
            
            var t1 = (-b - Math.Sqrt(delta)) / (2 * a);
            var t2 = (-b + Math.Sqrt(delta)) / (2 * a);
            return new Tuple<double?, double?>(t1, t2);
        }

        public override Intersection GetIntersection(Line line, double minDist, double maxDist)
        {
            // TODO: ADD CODE HERE
            double a = SemiAxesLength.X;
            double b = SemiAxesLength.Y;
            double c = SemiAxesLength.Z;
            Vector center = Center;

            var (t1, t2) = GetFirstLastIntersection(line);

            double tUse;
            if (t2 == null && t1 == null)
            {
                return new Intersection(false, false, this, line, 0, null, this.Material,  this.Color);
            }
            if (t2 == null)
            {
                tUse = (double)t1;
            }
            else if (t1 == null)
            {
                tUse = (double)t2;
            }
            else
            {
                tUse = Math.Min((double)t1, (double)t2);
            }

            var check = tUse >= minDist && tUse <= maxDist;

            if (!check)
            {
                return new Intersection(false, false, this, line, 0, null, this.Material,  this.Color);
            }
            
            return new Intersection(true, true, this, line, tUse, CalculateNormal(center, a, b, c, line.X0 + line.Dx * tUse), this.Material,  this.Color);
        }
    }
}
