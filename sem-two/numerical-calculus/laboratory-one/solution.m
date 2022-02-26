pkg load statistics;

a = [1 2 3];
b = [4; 5; 6];
%%disp(a);
%%disp(b);

a1 = [1, 2, 3];
b1 = [4 5 6]';
%%disp(a1);
%%disp(b1);

c = a * b;
%%disp(c);

d = [4 5 6]
%%disp(d);
d = b';
%%disp(d);

e = a .* d;
%%disp(e);
f = a .^ 2;
%%disp(f);

v = 1:6;
w = 2:3:10;

y = 10: -1 : 0;
%%disp(y);

q = exp(a);
%%disp(q);

exp(1);
%%disp(exp(1))

q = sqrt(a .^ 2);
%%disp(q);

m = max(a);
[m, k] = max(a);
%%disp(m);
%%disp(k);

h = [-2 -9 8];
k = abs(h);

mean(a);
geomean(a);

sum(a);
prod(a);

a = [1 2 13; 4 5 6; 7 8 9];
b = [4 8 12; -1 0 5; 2 3 8];
%%disp(a);
%%disp(b);

[m, n] = size(a);

t = b';
c = a * b;
disp(t);
disp(c);

d = a .* b
e = a .^ 2
size(a);
length(a);
m = mean(a);
m1 = mean(a, 2);
g = geomean(a);
s = sum(a);
s1 = sum(a, 2);
p = prod(a);
p1 = prod(a, 2);
max(a);
min(a);
diag(a);
m > 2;
a > b;
inv(b);
det(b);
f = abs(b);
b = [16 15 24]';
x = a\b;
triu(a);
tril(a);
m = [2 3 5; 7 11 13; 17 19 23];
m(2: 1);
m(:, 1);
m(2, :);
m(2, 1 : 2);
m(2, 2 : end);
m(2 : 3, 2 : 3);


eye(8);
eye(5, 7);
zeros(5, 7);
ones(7, 9);
M = magic(4);
sum(M); 
sum(M, 2); 
sum(diag(M)); 
sum(diag(fliplr(M)));

%% II  Polynomials
  %% 1.
  coefx = [2, -5, 0, 8];
  xval = 0: 0.1: 2; 
  polx = polyval(coefx, xval);
  disp(polx);
  
  %% 2.
  coefx = [1, -5, -17, 21];
  xval = 0: 0.1: 2; 
  polx = roots(coefx);
  disp(polx);
  
%% III Graphs
    %% 1.
      %% 1a.
      xval = 0 : 0.01 : 1;
      function fun = f1(x) 
        fun = exp(10 * x .* (x - 1)) .* sin(12 .* pi * x);
      endfunction;
      %%plot(f1(xval));
      
      %% 1b.
      xval = 0 : 0.01 : 1;
      function fun = f2(x) 
        fun = 3 .* exp(5 * x .^ 2 - 1) .* cos(12 .* pi * x);
      endfunction;
      %%plot(f2(xval));
      
    %% 2.
      tval = 0 : 0.1 : 10 .* pi;
      function xfun = xf(t, a, b)
        xfun = (a + b) .* cos(t) - b .* cos((a\b + 1) .* t);
      endfunction;
      function yfun = yf(t, a, b)
        yfun = (a + b) .* sin(t) - b .* sin((a\b + 1) .* t);
      endfunction;
      %%plot(yf(tval, 11, 272), xf(tval, 11, 272));
    
    %% 3.
      xval = 0 : 0.01 : 2 .* pi;
      function funOne = foo1(x)
        funOne = cos(x);
      endfunction;
      function funTwo = foo2(x)
        funTwo = sin(x);
      endfunction;
      function funThree = foo3(x)
        funThree = cos(2 .* x);
      endfunction;
      %%plot(foo1(xval));
      %%hold on;
      %%plot(foo2(xval));
      %%hold on;
      %%plot(foo3(xval));
      %%hold off;
    
    %% 4.
      xval = -1 : 0.25 : 1;
      function func = f3(x) 
        if (x >= -1) && (x <= 0)
          func = x .^ 3 + sqrt(1 - x);
        else 
          func = x .^ 3 - sqrt(1 - x);
        end;  
      endfunction;
      %%plot(f3(xval));
      
    %% 5.
      xval = 0 : 1 : 50;
      function oe = foo(x)
        remainder = mod(x, 2);
        if remainder == 0
          oe = x \ 2;
        else
          oe = 3 * x + 1;
        endif;
      endfunction;
      %%plot(foo(xval));
      
     %% 6.
      xval = 0 : 1 : 5;
      function rFoo = recursiveFoo(x)
        if (x <= 0)
          rFoo = 1;
        else
          rFoo = 1 + 1 \ (1 + recursiveFoo(x - 1));
        endif;
      endfunction;
      %%result = recursiveFoo(2);
      %%disp(result); 
      %%plot(recursiveFoo(xval));

    %% 7.
    figure(7)
    x7 = -2:2;
    y7 = -4:4;
    [X,Y] = meshgrid(x7,y7);
    R = -((X-1/2).^2+(Y-1/2).^2);
    Z = exp(R);
    mesh(X,Y,Z)


























