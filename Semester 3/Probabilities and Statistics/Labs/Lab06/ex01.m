clear

% a)
x = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];
alpha = input("Segnificance level: ");
n = length(x);
% the null hyp is H0: miu = 9
% the alternative hyp is H1: miu < 9
% left-tailed test for miu when sigma known
fprintf("This is a left-tailed test for miu when sigma known\n");
sigma = 5;
m0 = 9;  %this is given in H0
[H, P, CI, ZVAL] = ztest(x, m0, sigma, alpha, -1);
% 0 = don't reject; 1 = reject
% P = P-value
% Z = TS0
% -1 = left; 0 = two tailed; 1 = right

%ttalpha = quantile of order alpha for some law
z2 = norminv(alpha);
RR = [-inf, z2];


fprintf("The value of h is %d\n", H);
if H == 1
    fprintf("The null hyp is rejected\n");
    fprintf("The data suggests that the standard is not met\n");
else
    fprintf("The null hyp is not rejected\n");
    fprintf("The data suggests that the standard is met\n");
end

fprintf("The rejection region is (%4.4f, %4.4f)\n", RR);
fprintf("The observed value of the test static is %4.4f\n", ZVAL);
fprintf("The P-value of the test is %4.4f\n", P);



% b)
% H0: miu = 5.5
% H1: miu > 5.5
% right-tailed test for miu in the case sigma unknown
% T - student, T(n - 1) = T(length of data)

m01 = 5.5;
[H, P, CI, STATS] = ttest(x, m01, alpha, 1);
% STATS -> struct
% STATS.tstat
t2 = tinv(1 - alpha, n - 1);
RR = [t2, inf];

fprintf("\n\nThe value of h is %d\n", H);
if H == 1
    fprintf("The null hyp is rejected\n");
    fprintf("The data suggests that the standard is not met\n");
else
    fprintf("The null hyp is not rejected\n");
    fprintf("The data suggests that the standard is met\n");
end

fprintf("The rejection region is (%4.4f, %4.4f)\n", RR);
fprintf("The observed value of the test static is %4.4f\n", STATS.tstat);
fprintf("The P-value of the test is %4.4f\n", P);



% Remarks:
%   * theta = sigma^2 -> vartest
%   * theta = (sigma1)^2/(sigma2)^2 -> vartest2
%   * theta = miu1 - miu2 ttest2, ztest2