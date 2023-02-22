%P(X = 0)
y = binopdf(0, 3, 0.5);
fprintf("The probability of X being 0 is: %0.4f\n", y);

%P(X <> 0)
z = 1 - binopdf(0, 3, 0.5);
fprintf("The probability of X not being 0 is: %0.4f\n", z);

%d)
%P(X <= 2)
d1 = binocdf(2, 3, 0.5);

%P(X < 2) = P(X <= 1)
d2 = binocdf(1, 3, 0.5);


%e)
%P(X >= 1) = 1 - P(X < 1) = 1 - P(X <= 0)
e1 = 1 - binocdf(0, 3, 0.5);

%P(X > 1) = 1 - P(X <= 1)
e2 = 1 - binocdf(1, 3, 0.5);