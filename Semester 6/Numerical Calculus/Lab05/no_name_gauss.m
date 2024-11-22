function [x, nit] = no_name_gauss(A, b, x0, err, maxnit)
   D = diag(diag(A));
   L = tril(A, -1);
   U = triu(A, 1);

   M = D + L;
   N = -U;
   T = inv(M) * N;
   c = inv(M) * b;

   alpha = norm(T, inf);
   x = x0;

   for k = 1:maxnit
     xK = x;
     x = T * xK + c;

     if(norm(xK - x, inf) < err * (1 - alpha) / alpha)
      nit = k;
      return
     endif
   endfor
   nit = maxnit;
end
