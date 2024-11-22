A = diag(ones(1, 6) * -1, -1) + diag(ones(1, 6) * -1, 1) + diag(ones(1, 7) * 5, 0) + diag(ones(1, 4) * -1, 3) + diag(ones(1, 4) * -1, -3)

b = [3 2 2 1 2 2 3]';

gauss_elimination(A, b)


