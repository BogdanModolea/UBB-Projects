f = log(1 + x)

f1 = taylor(f, x, 0, 'Order', 3);
f2 = taylor(f, x, 0, 'Order', 6);

ezplot(f1, [-0.9, 1])

hold on

ezplot(f2, [-0.9, 1])


for k = 5 : 20
    T1 = taylor(log(1-x), x, 0, 'Order', k);
    T2 = taylor(log(1+x), x, 0, 'Order', k);
    vpa(subs((T2 - T1), x, 1/3), 5)
end


for k = 1 : 50
    T = taylor(f, x, 0, 'Order', k);
    v = vpa(subs(T, x, -0.0001), 11);
    v * 10
end