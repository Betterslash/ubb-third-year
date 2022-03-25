function div_diff = DividedDifferencesTable(x, f)
  n = length(x)
  div_diff = [f', zeros(n, n-1)];
  for k = 2 : n
    for i = 1 : n-k+1
      div_diff(i, k) = (div_diff(i + 1, k-1) - div_diff(i, k - 1)) / (x(i + k - 1) - x(i));
    endfor
  endfor
endfunction
  
  
function NIP = NewtonInterpolationPolynomial(x, fx, xx)
  n = length(x) - 1;
  table = DividedDifferencesTable(x, fx);
  m = length(xx);
  p = ones(1, m);
  s = table(1, 1) * ones(1, m);
  for j = 1 : m
    for i = 1 : n
      p(j) = p(j) * (xx(j) - x(i));
      s(j) = s(j) +  p(j) * table(1, i + 1);
    end
  end

  NIP = s;

endfunction 


  x = [1 1.5 2 3 4];
  fx = [0 0.17609 0.30103 0.47712 0.60206];
  i = 10:35;
  yi = i./10;

  xx = [2.5 3.25];
  
  #a)
  NIP = NewtonInterpolationPolynomial(x, fx, xx)
  
  
  #b)
  NIP = NewtonInterpolationPolynomial(x, fx, yi);
  E = max(abs(log10(yi) - NIP))  