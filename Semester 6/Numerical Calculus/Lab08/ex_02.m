xi = [0, 3, 5, 8, 13];
fi = [0, 225, 383, 623, 993];
dfi = [0, 77, 80, 74, 72];

[zi, d2] = divideddiff2(xi, fi, dfi);

dist = newton_int(zi, d2, 10)


d = divideddiff(xi, dfi);
speed = newton_int(xi, d, 10)

d = divideddiff(fi, dfi);
newton_int(fi, d, dist)

