N = input("N=");
C = rand(3, N);

Y = C < 0.5;
X = sum(Y);

histogram(X);