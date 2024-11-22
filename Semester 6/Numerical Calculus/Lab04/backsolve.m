function x=backsolve(U, b)
  x = zeros(size(b));
  n = length(b);

  for k = n:-1:1
    x(k) = (b(k) - U(k, k+1:n) * x(k+1:n)) / U(k, k);
  endfor
end
