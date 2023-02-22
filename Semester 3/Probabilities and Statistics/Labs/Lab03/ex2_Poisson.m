n = input("n = ");
p = input("p = ");
lambda = n * p;

K = 0 : n;

plot(K, binopdf(K, n, p));
hold on
plot(K, poisspdf(K, lambda));