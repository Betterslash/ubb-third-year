%pkg install -forge econometrics;
pkg load econometrics;
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
##  function x = resolver(N)
##    clf; hold on;
##    t0 = @(x) ones(size(x));
##    fplot(t0, [-1,1])
##    t1 = @(x) x;
##    fplot(t1, [-1,1])
##  
##    for i=2:N
##      aux = t1;
##      t1 = @(x) 2*x.*t1(x)-t0(x);
##      fplot(t1, [-1,1])
##      t0=aux;
##    endfor
##  endfunction
##  val = 0 : 3;
##  resolver(val);
  
% 3
##  function taylor(N)
##    clf;
##    hold on;
##    T = @(x) ones(size(x));
##    fplot(T, [-1, 3]);
##    for n = 1 : N
##      T = @(x) T(x) + (x.^n) / factorial(n);
##      fplot(T, [-1, 3]);
##    endfor
##    fplot(@exp, [-1, 3], '-r');
##  endfunction

  %fplot(taylor, [1 : 6]);
  
% 4
    a = ones(1, 7, 'double');
    %disp(a);
    cnst = 0.25;
    for t = 2 : 7
       a(t) = a(t) + (t .* cnst);
    endfor
    %disp(a);
    function foo = f(x)
      foo = sqrt(5 .* (x .^ 2) + 1);
    endfunction
    
    function getDelta = gtd(g, fg)
      if g == 0
        getDelta = 1;
      else
        getDelta = gtd(g - 1, fg) .* f(fg + 1) - gtd(g - 1, fg) .* f(fg);  
      endif
    endfunction
    
    function getFooArr = gfa(n)
      res = ones(1, n, 'double');
      for t = 1 : n
        res(t) = f(t);
      endfor
      getFooArr = res;
    endfunction
    
    result = zeros(7,7);
    % disp(result);
    
    for i = 1 : 7
      for j = 1 : ((7 - i) + 1)
        result(i,j) = gtd(j, a(i));
      endfor
    endfor
    
    %disp(gtd(1, 6));
    %disp(gfa(6));
    %disp(result);
    lines = ['f';
    'd(1,h)';
    'd(2,h)';
    'd(3,h)';
    'd(4,h)';
    'd(5,h)'; 
    'd(6,h)'];
    cols = num2str(a','%.2f');
    #prettyprint(result, cols, lines);
    
    
 ## Calculus Lab 2, Ex 4, Popa Alex Ovidiu 936/1
## Below you have a call example: 
  x1 = 1:0.25:2.5;
  f1 = @(x1) sqrt(5 * x1.^2 + 1);
  

function finite_diff = ex4(x, f)
  n = length(x);
  finite_diff = [f(x)', zeros(n, n-1)]; % append values of f(x) as a column vector at the start, one less column since we already have f
  for k = 2:n   # loop the triangle above the secondary diagonal of the matrix
    for i = 1:n-k+1
      finite_diff(i,k) = finite_diff(i+1, k-1) - finite_diff(i, k-1); # recurrence formula
    endfor
  endfor
  finite_diff = [x', finite_diff]; # append the values of x as a column vector 
endfunction   
  
t4 = ex4(x1, f1)
  
% 5
  x2 = [2 4 6 8];
  f2 = [4 8 14 16];
  

function div_diff = ex5(x, f)
  n = length(x);
  div_diff = [f', zeros(n, n-1)]; % append values of f as a column vector at the start, one less column since we already have f
  for k = 2:n  # loop the triangle above the secondary diagonal of the matrix
    for i = 1:n-k+1
      div_diff(i,k) = (div_diff(i+1,k-1)-div_diff(i,k-1))/(x(i+k-1)-x(i)); # recurrence formula
    endfor
  endfor
  div_diff = [x', div_diff]; # append the values of x as a column vector 
endfunction

  t5 = ex5(x2, f2)  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    