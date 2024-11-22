function x=gauss_elimination(A, b)
  x = zeros(size(b));
  [r, n] = size(A);
  A = [A, b];

  for j=1:n-1
    [t, p] = max(abs(A(j:n, j)));
    p = p + j - 1;

    if p != j
      A([p, j], :) = A([j, p], :);
    endif

    for i=j+1:r
      m = A(i, j) / A(j, j);
      A(i, :) = A(i, :) - m * A(j, :);
    endfor
  endfor
  x = backsolve(A(:,1:n), A(:,n+1));
end
