xi = [-1, -1/2, 0, 1/2, 1, 3/2];
fi = xi.*sin(pi.*xi);
x = linspace(-1, 3/2);

f = x.*sin(pi.*x);

plot(x, f, xi, fi, 'o')

s = spline(xi, fi, x);
hold on
plot(x, s)

sc = spline(xi, [pi, fi, -1], x);
plot(x, sc)

