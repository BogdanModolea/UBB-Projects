f = @(x)sin(x)./x;

integral(f, 0, 1)

[I, nf] = romberg(f, 0, 1, 10^(-6), 25)
I
