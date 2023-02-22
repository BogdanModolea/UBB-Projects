% help rand

p = input("p = ");
N = input("Nr. of simulations = ");

U = rand(1, N);
X = (U < p);
U_X = unique(X)
n_X = hist(X, length(U_X));
relativ_frequencies = n_X / N