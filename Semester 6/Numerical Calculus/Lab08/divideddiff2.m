function [z, t]=divideddiff2(x, f, fd)
  n = length(x);
  z = repelem(x, 2);
  t = zeros(2*n);
  t(:,1) = repelem(f, 2)';
  t(1:2:2*n-1, 2) = fd';
  t(2:2:2*n-2, 2) = (diff(f)./diff(x))';

  for k = 3:2*n
    t(1:2*n-k+1, k) = diff(t(1:2*n-k+2, k-1))./(z(k:2*n)-z(1:2*n-k+1))';
  endfor
end
