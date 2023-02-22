clear;

% x = vector of data
% miu = population mean (theoretical mean)
% xbar = sample mean (help mean)
% sigma^2 = population variance (th. var.)
% s^2 = sample variance ( var(x) )
% sigma = population standard deviation
% s = sample standard deviation ( std(x) )
% Zalpha = the quantile of order alpha of the N(0,1) law
% ! quatiles: ____inv(...)
% n = sample size



% a)
x = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];

n = length(x);
oneminusalpha = input("Confidence level: ");
alpha = 1 - oneminusalpha;
xbar = mean(x);
sigma = 5;

% orders: 1-alpha/2; alpha/2
% z(1 - alpha/2) = norminv(1 - alpha/2)
m1 = xbar - sigma/sqrt(n) * norminv(1 - alpha/2);
m2 = xbar - sigma/sqrt(n) * norminv(alpha/2);

fprintf("The conf. int. for pop. mean when sigma is known is (%4.3f, %4.3f)\n", m1, m2);



% b)

s = std(x);

m1 = xbar - s/sqrt(n) * tinv(1-alpha/2, n - 1);
m2 = xbar - s/sqrt(n) * tinv(alpha/2, n - 1);
fprintf("The conf. int. for pop. mean when sigma is unknown is (%4.3f, %4.3f)\n", m1, m2);



% c)
% for the variance

s2 = var(x);
%chi2inv(alpha/2, n - 1);

sigma2_1 = (n - 1) * s2 / chi2inv(1 - alpha/2, n - 1);
sigma2_2 = (n - 1) * s2 / chi2inv(alpha / 2, n - 1);
fprintf("The conf. int. for the variance is (%4.3f, %4.3f)\n", sigma2_1, sigma2_2);

% for standard dev.


fprintf("The conf. int. for pop. standard dev. is (%4.3f, %4.3f)\n", sqrt(sigma2_1), sqrt(sigma2_2));