p = [1, -5, -16, 16, -17, 21];

% b)
polyval(p, -2.5);

% a)
x = -4:0.1:7.2;
px = polyval(p, x);

plot(x, px);

% c)

roots(p)
polyval(p, 7)