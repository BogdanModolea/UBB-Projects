A = [10, 7, 8, 7; 7, 5, 6, 5; 8, 6, 10, 9; 7, 5, 9, 10];
b = [32, 23, 33, 31]';

%a)

x = inv(A) * b;


% b)
bt = [32.1, 22.9, 33.1, 30.9]';
xt = inv(A) * bt;

err1 = norm(b - bt) / norm(b);

err2 = norm(x - xt) / norm(x);

% c)

At = [10, 7, 8.1, 7.2; 7.8, 5.04, 6, 5; 8, 5.98, 9.89, 9; 6.99, 4.99, 9, 9.98];

err3 = norm(A - At) / norm(A);

xt2 = inv(At) * b;
err4 = norm(x - xt2) / norm(x);


% d)
condA = norm(A) * norm(inv(A));

