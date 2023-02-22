%solving ex 1 for the normal form

miu = input("miu = ");           % mean
sigma = input("sigma = ");       % standard deviation

%1) a
%P(x <= 0) -> normcdf(0, miu, sigma)

p1a = normcdf(0, miu, sigma);
%P(x >= 0)
p2a = 1 - p1a;



%1) b
%P(-1 <= x <= 1) = F(1) - F(-1) = normcdf(1, miu, sigma) - normcdf(-1, miu, sigma) 
%P(a < X <= b) = F(b) - F(a)

p1b = normcdf(1, miu, sigma) - normcdf(-1, miu, sigma);

%P(X <= -1 or X >= 1) = 1 - P(-1 <= X <= 1)
p2b = 1 - p1b;



%1) c
alpha = input("alpha = ");
xalpha = norminv(alpha, miu, sigma);



%1) d
beta = input("beta = ");
xbeta = norminv(1 - beta, miu, sigma);