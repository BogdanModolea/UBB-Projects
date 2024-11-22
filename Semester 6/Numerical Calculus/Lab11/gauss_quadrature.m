function [I, gn, gc] = gauss_quadrature(f, n, method)
  alpha = zeros(1, n);

  switch method
    case 1 #Legendre
      beta = [2, (4-(1:n-1).^(-2)).^(-1)];

    case 2 #Chebyshev 1st
      beta = [pi, 1/2, 1/4*ones(1, n-2)];
    case 3 #The others
      beta = [pi/2, 1/4 * ones(1, n-1)];
  endswitch
  J = diag(alpha) + diag(sqrt(beta(2:n)), 1) + diag(sqrt(beta(2:n)), -1);
  [v, d] = eig(J);
  gn = diag(d);
  gc = beta(1) * v(1,:).^2;
  I = gc * f(gn);
end
