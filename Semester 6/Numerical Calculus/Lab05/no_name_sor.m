function [x, nit] = no_name_sor(A, b, w, x0, err, maxnit)
  D = diag(diag(A));
  U = triu(A, 1);
  L = tril(A, -1);

  M = D / w + L;
  N = ((1 - w)/w) * D - U;

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
