disp("Start");
function div_diff = diff(x, f)
  n = length(x);
  div_diff = [f', zeros(n, n-1)];
  for k = 2:n 
    for i = 1:n-k+1
      div_diff(i,k) = (div_diff(i+1,k-1)-div_diff(i,k-1))/(x(i+k-1)-x(i)); 
    endfor
  endfor
endfunction
  
  
function NIP = NewtonInterpolationPolynomial(x, fx, xx)
  n = length(x) - 1;
  table = diff(x, fx);
  m = length(xx);
  p = ones(1, m);
  s = table(1, 1) * ones(1, m);
  for j = 1 : m
    for i = 1 : n
      p(j) = p(j) * (xx(j) - x(i));
      s(j) = s(j) +  p(j) * table(1, i + 1);
    endfor
  endfor
  NIP = s;
endfunction 

%%--------------------
##disp("\n Ex 1 : ");
##x = [1 1.5 2 3 4];
##fx = [0 0.17609 0.30103 0.47712 0.60206];
##i = 10:35;
##yi = i./10;
##
##xx = [2.5 3.25];
##  
####a)
##NIP = NewtonInterpolationPolynomial(x, fx, xx)
##  
##  
####b)
##NIP = NewtonInterpolationPolynomial(x, fx, yi);
##E = max(abs(log10(yi) - NIP))  

%%-----------------
##    
##disp("\n Ex 2:");
###a)    
##x = [1 2 3 4 5];
##fx = [22 23 25 30 28];
##
##yi = [2.5];
##
##NIP = NewtonInterpolationPolynomial(x, fx, yi)
##
###b)
##z = 0 : 0.01 : 6;
##NIP = NewtonInterpolationPolynomial(x, fx, z);
##
##plot(x, fx, 'r*');
##hold on;
##plot(z, NIP, 'g');
##    
%------------
##
##disp("\n Ex 3 :");
## f = @(x)(exp(sin(x)));
## x = linspace(0, 6, 13);
## y = f(x);
## xx = 0 : 0.1 : 6;
##
## NIP = NewtonInterpolationPolynomial(x, y, xx);
##
## plot(x, f(x),'r*');
## hold on;
## plot(xx, f(xx), 'g');
## hold on;
## plot(xx, NIP, 'b');
 
 
%-------------------

disp("\nEx 4 :");
  function z = AitkenAlgorithm(x, y, a)
  
    er = 1e-3; 
    [z, I] = sort(abs(x - a));
    x = x(I);
    y = y(I);
    n = length(x);
    ak = zeros(n, n);
    ak(:, 1) = y';
    
    for i = 1 : n
    
        for j = 1 : i - 1
           ak(i, j + 1) = (1 / (x(i) - x(j))) * (ak(j,j) * (x(i) - a) - ak(i, j) * (x(j) - a));
        endfor
        
        if i > 1 && abs(ak(i - 1, i - 1) - ak(i, i)) <= er
              z = ak(i, i);
              disp(i);        
              return
        endif
              
    endfor
       
  endfunction

  
  x = [64 81 100 121 144 169];
  y = [8 9 10 11 12 13];

  a = 115;

  ans = AitkenAlgorithm(x, y, a)

  err = abs(ans-sqrt(a))