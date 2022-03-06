% 1
    
    x = 0 : 0.1 : 1;

    % l1
    function lOne = l1(x)
      lOne = x;
    endfunction;

    % l2
    function lTwo = l2(x)
      lTwo = (3\2) .* (x .^2) - (1\2);
    endfunction;
  
    % l3
    function lThree = l3(x)
      lThree = (5\2) .* (x .^3) - (3\2) .* (x .^ 2);
    endfunction;
    
    % l4
    function lFour = l4(x)
      lFour = (35\8) .* (x .^4) - (15\4) .* (x .^ 2) + (3\8);
    endfunction;
    
##    disp(x);
##    
##    subplot(2, 2, 1);
##    plot(l1(x)); 
##    
##    subplot(2, 2, 2);
##    plot(l2(x));
##    
##    subplot(2, 2, 3);
##    plot(l3(x));
##    
##    subplot(2, 2, 4);
##    plot(l4(x));    
    
% 2
  % a 
  function Tfun = T(n, t)
    Tfun = cos(n * acos(t));
  endfunction;  
##  
##  t = -1 : 0.1 : 1;
##  
##  plot(T(1, t));
##  hold on;
##  plot(T(2, t));
##  hold on;
##  plot(T(3, t));
##  hold off;
  
  % b
  function x = resolver(N)
    clf; hold on;
    t0 = @(x) ones(size(x));
    fplot(t0, [-1,1])
    t1 = @(x) x;
    fplot(t1, [-1,1])
  
    for i=2:N
      aux = t1;
      t1 = @(x) 2*x.*t1(x)-t0(x);
      fplot(t1, [-1,1])
      t0=aux;
    endfor
  endfunction
  val = 0 : 3;
  resolver(val);
  
% 3
  function taylor(N)
    clf;
    hold on;
    T = @(x) ones(size(x));
    fplot(T, [-1, 3]);
    for n = 1 : N
      T = @(x) T(x) + (x.^n) / factorial(n);
      fplot(T, [-1, 3]);
    endfor
    fplot(@exp, [-1, 3], '-r');
  endfunction

  fplot(taylor, [1 : 6]);
  

  
    