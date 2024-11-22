f=@(x) x-exp(-x);
fplot(f, [-1, 2])
grid on
[z, ni] = secant(f, 0, 1, 10^(-5), 50)
