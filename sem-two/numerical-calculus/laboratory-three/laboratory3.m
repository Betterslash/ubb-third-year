## Functions z(Ai) is used for computing L(lagrange)

##1
clear

##sum from 1 to n with i != j
function z = Ai(i,x)
  m = length(x);
  p = 1;
for j = 1:m
  if i~=j
    p = p*(x(i)-x(j));
  endif
end
z = 1/p; ## inverse the result
endfunction

%%Lmf(x)
%%x = xx is my input
%%xi = x(i)
function L = lagrange(x,y,xx)  

m = length(x);
P = zeros(1,m);
N = length(xx);
L = zeros(1,N);
for j = 1:N
  s1 = 0;
  s2 = 0;
  for i = 1:m
    P(i) = Ai(i,x);
  s1 = s1 + P(i)*y(i)/(xx(j)-x(i));
  s2 = s2 + P(i)/(xx(j)-x(i));
endfor
L(j) = s1/s2;
end
endfunction

x = 1930:10:1980; ## years going from 10 to 10
y = [123203, 131669, 150697, 179323, 203212, 226505]; ##values from the table
xx = [1955,1995]; ## required years
  
disp("Ex 1 : ")  
result = lagrange(x,y,xx);
disp("For 1955 : ");
disp(floor(result(1)));
disp("For 1995 : ");
disp(floor(result(2)));
disp("\n")

##2
clear


function z = Ai(i,x)
  m = length(x);
  p = 1;
for j = 1:m
  if i~=j
    p = p*(x(i)-x(j));
  endif
end
z = 1/p;
endfunction

function L = lagrange(x,y,xx)  

m = length(x);
P = zeros(1,m);
N = length(xx);
L = zeros(1,N);
for j = 1:N
  s1 = 0;
  s2 = 0;
  for i = 1:m
    P(i) = Ai(i,x);
  s1 = s1 + P(i)*y(i)/(xx(j)-x(i));
  s2 = s2 + P(i)/(xx(j)-x(i));
endfor
L(j) = s1/s2;
end
endfunction

x = [225 256 289]; 
y = [15 16 17];
xx = [115];

disp("Ex 2 :")  
lagrange(x,y,xx)
disp("\n")

##3
clear


function z = Ai(i,x)
  m = length(x);
  p = 1;
for j = 1:m
  if i~=j
    p = p*(x(i)-x(j));
  endif
end
z = 1/p;
endfunction

function L = lagrange(x,y,xx)  

m = length(x);
P = zeros(1,m);
N = length(xx);
L = zeros(1,N);
for j = 1:N
  s1 = 0;
  s2 = 0;
  for i = 1:m
    P(i) = Ai(i,x);
  s1 = s1 + P(i)*y(i)/(xx(j)-x(i));
  s2 = s2 + P(i)/(xx(j)-x(i));
endfor
L(j) = s1/s2;
end
endfunction

disp("Ex 3 : ")

f = @(a) (1 + cos(pi*a)) ./ (1 + a); ## lambda of function

x = linspace(0, 10, 21); ## computing function for the equally spaced points
y = f(x); 

xx = linspace(0, 10, 100); ## take another 10 points qually spaced

plot(xx, f(xx)); ## initial function ploting
hold on; ## wait for plot to be overriden

L = lagrange(x, y, xx); ## apply lagrange interpolation

plot(xx, L,'r');   ##plot on the same table
hold off;
##the aproximation becomes more different as we continue


##4

clear 

function z = compute_A_i(i,x)
  m = length(x);
  p = 1;
  for j = 1:m
    if i~=j
      p = p*(x(i)-x(j));
    endif
  end
  z = 1/p;
endfunction

function L = lagrange(x,y,xx)  
  m = length(x);
  P = zeros(1,m);
  N = length(xx);
  L = zeros(1,N);
  for j = 1:N
    s1 = 0;
    s2 = 0;
    for i = 1:m
      P(i) = compute_A_i(i,x);
    s1 = s1 + P(i)*y(i)/(xx(j)-x(i));
    s2 = s2 + P(i)/(xx(j)-x(i));
  endfor
  L(j) = s1/s2;
  end
endfunction

f = @(a) 1 ./ (1 + a .^ 2);

function max = compute_error_n(f, y, n, L) ##compute error with the formula
  max = 0;
  for i = 1:100
    calcul = abs(f(i / 10 - 5) - L(i));
    if (calcul > max)
      max = calcul;
    endif
  endfor
endfunction

%% max

N = 2:2:8;
for n = N
  x = linspace(-5, 5, n + 1);
  y = f(x);
  xx = -5:0.1:5;
  L = lagrange(x, y, xx);
  e = compute_error_n(f, y, n, L) ##display error
  plot(xx, L);
  hold on;
  plot(xx, f(xx)); ## if we wanna see function with error
  legend;
  title(strcat("n=", num2str(n), " e=", num2str(e)))
endfor
