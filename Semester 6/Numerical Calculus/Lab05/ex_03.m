n = 400;
[A, b] = no_name(n);
x0 = zeros(size(b));
maxnit = 100;
err = 10^-5;

[x, nit] = no_name_gauss(A, b, x0, err, maxnit);

x

nit
