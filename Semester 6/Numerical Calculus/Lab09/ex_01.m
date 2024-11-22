xi = -2:4;
fi = (xi + 1)./(3*xi.^2+2*xi+1);
x = linspace(-2,4);

f =(x+1)./(3*x.^2+2*x+1);
plot(xi, fi, 'o', x, f)

d = divideddiff(xi, fi);
hold on

plot(x, newton_int(xi, d, x))


dfi = (-3*xi.^2-6*xi-1)./((3*xi.^2+2*xi+1).^2);
[zi, d2] = divideddiff2(xi, fi, dfi);
plot(x, newton_int(zi, d2, x))


s = spline(xi, fi, x);
plot(x, s)
