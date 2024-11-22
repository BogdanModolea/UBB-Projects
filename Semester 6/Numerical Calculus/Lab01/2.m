x = linspace(0, 2 * pi, 150);

plot(x, sin(x), x, sin(2*x), x, sin(3*x))

clf

plot(x, sin(x))
hold on
plot(x, sin(2*x))
plot(x, sin(3*x))
hold off

clf

f = @(x) sin(x);
f([1, 2]);

fplot(f, [0, 2*pi])
hold on
fplot(@(x) sin(2*x), [0, 2*pi])

clf 

subplot(3, 1, 1)
fplot(@(x) sin(x), [0, 2*pi])
subplot(3, 1, 2)
fplot(@(x) sin(2*x), [0, 2*pi])
subplot(3, 1, 3)
fplot(@(x) sin(3*x), [0, 2*pi])