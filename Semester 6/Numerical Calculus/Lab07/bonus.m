xi = [39, 41, 43, 45, 47, 49, 51];
fi = [0.6293204, 0.6560590, 0.6819984, 0.7071068, 0.7313597, 0.7547096, 0.7771460];

d = divideddiff(xi, fi);
xx = linspace(0, 1, 100);

newton_int(xi, d, 40)
sin(0.698131701)

newton_int(xi, d, 44)
sin(0.767944871)

newton_int(xi, d, 50)
sin(0.872664626)
