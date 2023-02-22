p = input("p = ");

for n = 1:5:1000
    k = 0:n;
    plot(k, binopdf(k, n, p))
    pause(0.5)
end