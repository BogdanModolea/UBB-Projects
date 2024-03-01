using System;
using System.Runtime.InteropServices;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            return -n * viewPlaneSize / imgSize + viewPlaneSize / 2;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = new Intersection();

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            var line = new Line(point, light.Position);
            foreach (var geometry in geometries)
            {
                if (geometry.GetType() == typeof(RawCtMask))
                {
                    continue;
                }
                var intersection = geometry.GetIntersection(line, 0, (point - light.Position).Length());
                if (intersection.Visible)
                {
                    return false;
                }
            }
            return true;
        }

        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color(0.2, 0.2, 0.2, 1.0);
            var viewParallel = (camera.Up ^ camera.Direction).Normalize();

            var image = new Image(width, height);

            for (var i = 0; i < width; i++)
            {
                for (var j = 0; j < height; j++)
                {
                    // ADD CODE HERE: Implement pixel color calculation

                    var x0 = camera.Position;
                    var x1 = camera.Position +
                             camera.Direction * camera.ViewPlaneDistance +
                             viewParallel * ImageToViewPlane(i, width, camera.ViewPlaneWidth) +
                             camera.Up * ImageToViewPlane(j, height, camera.ViewPlaneHeight);

                    var intersection = FindFirstIntersection(new Line(x0, x1), camera.FrontPlaneDistance, camera.BackPlaneDistance);
                    if (intersection.Valid && intersection.Visible)
                    {
                        var color = new Color();

                        foreach (var light in lights)
                        {
                            var colorFromLight = new Color();
                            colorFromLight += intersection.Material.Ambient * light.Ambient;

                            if (IsLit(intersection.Position, light))
                            {
                                var T = (light.Position - intersection.Position).Normalize();
                                var N = intersection.Normal;
                            
                                if (N * T > 0)
                                {
                                    colorFromLight += intersection.Material.Diffuse * light.Diffuse * (N * T);
                                }
                                
                                var E = (camera.Position - intersection.Position).Normalize();
                                var R = (N * (N * T) * 2 - T).Normalize();
                                
                                if (E * R > 0)
                                {
                                    colorFromLight += intersection.Material.Specular * light.Specular * Math.Pow((E * R), intersection.Material.Shininess);
                                }
                                
                                colorFromLight *= light.Intensity; 
                            }
                            
                            color += colorFromLight;
                        }

                        image.SetPixel(i, j, color);
                    }
                    else
                    {
                        image.SetPixel(i, j, background);
                    }
                }
            }

            image.Store(filename);
        }
    }
}