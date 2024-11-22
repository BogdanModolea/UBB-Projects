xi = [0, 1, 2];
fi = 1./(1+xi);

d = divideddiff(xi, fi);

xx = linspace(0, 2);
plot(xx, newton_int(xi, d, xx))

dfi = -1./(1+xi).^2;
[zi, d2] = divideddiff2(xi, fi, dfi);

hold on

plot(xx, newton_int(zi, d2, xx))
