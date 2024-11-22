function a = aitken(xi, fi, x)
   p(:,1) = fi;
   n = length(xi);

   for i = 1 : n
     for j = 1 : n-1
       p(i, j + 1) = ((x - xi(j))*p(i, j) - (x-xi(i))*p(j,j))/(xi(i)-xi(j));
     endfor
   endfor

   a = p(n, n);
end
