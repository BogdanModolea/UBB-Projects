xi = [0, 1/3, 1/2, 1];
fi = cos(pi*xi);

d = divideddiff(xi, fi);

xx = linspace(0, 1, 100);


%plot(xx, cos(pi*xx), xx, newton_int(xi, d, xx));


newton_int(xi, d, 1/5)
cos(pi/5)


