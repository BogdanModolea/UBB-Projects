x = [0, 1, 2];
f=1./(1+x);

% a)
divideddiff(x, f);

% b)
fd = -1./(x+1).^2;
[z, t] = divideddiff2(x, f, fd);
z;

t;


% c)

xx = 1:1/10:2;
%xx = linspace(1, 2, 11);
ff=1./(1+xx);

divideddiff(xx, ff)
ffd = -1./(xx+1).^2;

[zz, tt] = divideddiff2(xx, ff, ffd);
zz

tt
