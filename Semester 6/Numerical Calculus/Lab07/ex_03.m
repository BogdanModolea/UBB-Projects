xi = [1000, 1010, 1020, 1030, 1040, 1050];
fi = [3, 3.0043214, 3.0086002, 3.0128372, 3.0170333, 3.0211893];

d = divideddiff(xi, fi);
xx = linspace(0, 1, 100);


for k = 1001 : 1009
  k, newton_int(xi, d, k), log10(k)
endfor
