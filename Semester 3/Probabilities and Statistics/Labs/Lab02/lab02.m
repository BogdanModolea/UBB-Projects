n = input("n=");
p = input("p=");
x = 0:n;
y = binopdf(x, n, p);

plot(x, y, "*")
