A = diag(ones(1, 6) * -1, -1) + diag(ones(1, 6) * -1, 1) + diag(ones(1, 7) * 5, 0) + diag(ones(1, 4) * -1, 3) + diag(ones(1, 4) * -1, -3);
b = [3 2 2 1 2 2 3]';


x0 = zeros(size(b));
maxnit = 100;
err = 10^-5;

M = diag(diag(A));
N = -A + M;
T = inv(M) * N;

p = max(abs(eig(T)));
w = 2 / (1 + sqrt(1 - p^2));

[x, nit] = no_name_sor(A, b, w, x0, err, maxnit);

x
nit
