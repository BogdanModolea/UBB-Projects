n = input("n=");
p = input("p=");
x = 0:n;
y = binopdf(x, n, p);

plot(x, y, "*")

hold on

xx = 0:0.1:n;
z = binocdf(xx, n, p);


plot(xx, z);