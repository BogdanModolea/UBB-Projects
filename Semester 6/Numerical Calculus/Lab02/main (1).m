syms x
f = sin(x)
fs3 = taylor(f, x, 0, 'Order', 4);
fs5 = taylor(f, x, 0, 'Order', 6);

ezplot(f, [-pi, pi])

hold on

ezplot(fs3, [-pi, pi])
ezplot(fs5, [-pi, pi])



vpa(sin(pi/5), 5)
vpa(subs(fs5, x, pi/5), 5)


f = sin(x)
for k = 1 : 20
    T = taylor(f, x, -pi / 3, 'Order', k);
    vpa(subs(T, x, -pi / 3), 5)
end