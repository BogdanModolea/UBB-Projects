xi = [100, 121, 144];
fi = [10, 11, 12];

ans = Lagrange_int(xi, fi, 118);

abs(sqrt(118) - ans) / abs(sqrt(118))
