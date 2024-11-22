function [x, nit] = no_name_jacobi(A, b, x0, err, maxnit)
   M = diag(diag(A));
   N = -A + M;
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
