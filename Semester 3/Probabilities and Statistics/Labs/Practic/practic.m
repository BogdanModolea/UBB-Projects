clear

x = [1001.7, 975.0, 978.3, 988.3, 978.7, 988.9, 1000.3, 979.2, 968.9, 983.5, 999.2, 985.6];

fprintf("a)\n");
alpha1 = 0.05;
n = length(x);
t1 = tinv(1 - alpha1/2, n - 1);
m = mean(x);
s = std(x);

left = m - (s/sqrt(n))*t1;
right = m + (s/sqrt(n))*t1;

fprintf("The 95 confidence interval is (%.4f, %.4f)\n", left, right);


fprintf("\nb)\n");
fprintf("Right-tailed test\n");
miu = 995;
alpha2 = 0.01;
[H, P, CI, stats] = ttest(x, miu, alpha2, 1);
fprintf("The null hyp is H0: miu = 995\n");
fprintf("The alternative hyp is H1: miu > 995\n");

fprintf("The H value is %d\n", H);

if H == 1
    fprintf("The null hyp is rejected\n");
    fprintf("The data suggests that the standard is not met\n");
else
    fprintf("The null hyp is not rejected\n");
    fprintf("The data suggests that the standard is met\n");
end


RR = tinv(1-alpha2, n-1);
fprintf('Observed value is %1.4f\n', stats.tstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (%.4f, inf)\n', RR);
