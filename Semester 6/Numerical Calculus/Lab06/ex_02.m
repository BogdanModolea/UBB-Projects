xi = linspace(-2, 4, 10);
fi = (xi + 1)./ (3*xi.^2+2*xi+1);
x = linspace(-2, 4, 500);
f = (x+1)./(3*x.^2+2*x+1);
L9f = Lagrange_int(xi, fi, x);

plot(x, f, x, L9f);

plot(x, abs(f - L9f))

max(abs(f - L9f))
