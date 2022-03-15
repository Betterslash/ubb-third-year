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



f = @(a) (1 + cos(pi*a)) ./ (1 + a);

x = linspace(0, 10, 21); 
y = f(x); 

xx = linspace(0, 10, 100);

plot(xx, f(xx)); 
hold on;

L = lagrange(x, y, xx);

plot(xx, L,'r');   