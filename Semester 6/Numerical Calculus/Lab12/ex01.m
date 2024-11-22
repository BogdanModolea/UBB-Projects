f=@(x) x-exp(-x);
fplot(f, [-1, 2])
grid on
[z, ni] = bisect(f, 0, 1, 10^(-5), 50)
