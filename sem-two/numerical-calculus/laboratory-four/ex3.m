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

    # divided differences table
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
 

 f = @(x)(exp(sin(x)));
 x = linspace(0, 6, 13);
 y = f(x);
 xx = 0 : 0.1 : 6;

 NIP = NewtonInterpolationPolynomial(x, y, xx);

 plot(x, f(x),'r');
 hold on;
 plot(xx, f(xx), 'g');
 hold on;
 plot(xx, NIP, 'b');
 