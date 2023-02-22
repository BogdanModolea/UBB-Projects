clear

p = input("p = ");
n = input("Nr. of trials = ");
N = input("Nr. of simulations = ");

for i = 1:N
   U = rand(n, 1);
   X(i) = sum(U < p);
end

k = 0:n;
U_X = unique(X)
n_X = hist(X, length(U_X));
relativ_frequencies = n_X / N
plot(U_X, relativ_frequencies, "*", k, binopdf(k, n, p), "r*")