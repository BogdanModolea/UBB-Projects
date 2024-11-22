[x, y] = meshgrid(linspace(-2, 2), linspace(0.5, 4.5));

f = sin(exp(x)).*cos(log(y));

mesh(x, y, f);

plot3(x, y, f)