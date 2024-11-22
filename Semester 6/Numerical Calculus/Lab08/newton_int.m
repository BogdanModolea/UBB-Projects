function f = newton_int(xi, d, x)
  for k = 1 : length(x)
    v = x(k) - xi;
    c = [1, cumprod(v(1:length(xi) - 1))];

    f(k) = d(1,:) * c';
  endfor
end
