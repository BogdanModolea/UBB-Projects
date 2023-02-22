clear

p = input("p = ");
N = input("Nr. of simulations = ");

for i = 1:N
    X(i) = 0;
    while rand >= p
        X(i) = X(i) + 1;
    end
end

k = 0:15;
U_X = unique(X);
n_X = hist(X, length(U_X));
relativ_frequencies = n_X / N;
plot(U_X, relativ_frequencies, "*", k, geopdf(k, p), "r*")