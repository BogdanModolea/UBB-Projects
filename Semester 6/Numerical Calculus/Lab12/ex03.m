f=@(x) x-exp(-x);
fd = @(x) 1 + exp(-x);
fplot(f, [-1, 2])
grid on
[z, ni] = newton(f, fd, 0, 10^(-5), 50)
