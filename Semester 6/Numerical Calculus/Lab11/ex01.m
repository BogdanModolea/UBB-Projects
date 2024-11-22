ans = pi * sqrt(3) / 9

f=@(x) 1./(2+sin(x));

[I,nf] = romberg(f,0, pi/2, 10^(-6), 50);

I
nf
