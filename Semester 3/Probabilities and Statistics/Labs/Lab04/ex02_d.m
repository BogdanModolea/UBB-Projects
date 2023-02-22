clear

n = input("Nr. of trials = ");
p = input("p = ");
N = input("Nr. of simulations = ");

for i = 1:N
    for j = 1:n
       X(j) = 0;
       while rand >= p
           X(j) = X(j) + 1;
       end
    end
    Y(i) = sum(X); % Y ~ Nbin
end

k = 0:150;
U_Y = unique(Y);
n_Y = hist(Y, length(U_Y));
relativ_frequencies = n_Y / N;
plot(U_Y, relativ_frequencies, "*", k, nbinpdf(k, n, p), "r*")