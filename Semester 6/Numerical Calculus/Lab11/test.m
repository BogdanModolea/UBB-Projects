f = @(x) e.^cos(x);

integral(f, 0, pi/4)

f2 = @(x) e.^(cos((pi/8) * (x+1))) * (pi/8);

[I, gn, gc] = gauss_quadrature(f2, 5, 1);
I
