disp("Start ...");
% t is node interpolation vector
% d is the value of the function calc in t vector
% v is the value of the derivative of the function calc in t vector
% x is the vector of the elements in witch we want to aproximate the function

function [H]=HermitePol(t,d,v,x)

l=length(t);
 
z=zeros(1,2*l);  z(1:2:end)=t; z(2:2:end)=t; % sau z=reshape([t;t], 1, []);
f=zeros(1,2*l);  f(1:2:end)=d; f(2:2:end)=d; % sau f=reshape([d;d], 1, []);

m=zeros(2*l,2*l); % m = matrix of divided diff table
m(:,1)=f';
m(1:2:end,2)=v';
m(2:2:2*l-1,2)=(d(2:l)-d(1:l-1))./(t(2:l)-t(1:l-1));
for k=3:2*l
    for i=1:2*l-k+1
        m(i,k)=(m(i+1,k-1)-m(i,k-1))/(z(i+k-1)-z(i));
    end
end

lx=length(x);   # the Hermite interpolation
p=ones(lx,1);
s=m(1,1)*ones(lx,1);   % --> f(z0) = m(1,1)
for j=1:lx
  for i=1:2*l-1
    p(j)=p(j)*(x(j)-z(i));
    s(j)=s(j)+p(j)*m(1,i+1);
  end
end

H=s';
endfunction
%% ex -> 1

disp("Ex 1 : ");
t = [0 3 5 8 13];
d = [0 225 383 623 993];
v = [75 77 80 74 72];
x = 10; ##the time
## we will apply the formula that speed is distance / time

disp("Distnace is : ");
[H] = HermitePol(t,d,v,x);
disp(H);
disp("Speed is : ")
speed = H ./ x;
disp(speed); 


%%ex -> 2
disp("\nEx 2 : ");
t = [1 2];
d = [0 0.6931];
v = [1 0.5];
x = 1.5;

H = HermitePol(t,d,v,x)

err = abs(log(x) - H)

%%ex -> 3
disp("\nEx 3 : ");
t = linspace(-5,5,15);
f = @(x)sin(2*x);

d = f(t);

deriv = @(x)2*cos(2*x);

v = deriv(t);

%%x = linspace(-5,5,15);
x = linspace(-5,5,150);

H = HermitePol(t,d,v,x);

hold on 
##plot(x, f(x),'r')
plot(x, f(x),'r*')
plot(x, H, 'g')

##%% ex -> 4
##disp("\nEx 4 : ");
##t = [8.3 8.6];
##d = [17.56492 18.50515];
##v = [3.116256 3.151762];
##x = 8.4;
##
##H = HermitePol(t,d,v,x)
##
##err = abs(x*log(x) - H)
##
##%%ex -> 5
##disp("\nEx 5 : ");
##x1 = [1 1.05 1.07 1.10];
##f = @(x)3*x.*exp(x)-exp(2*x);
##d = @(x)3*exp(x) + 3*x.*exp(x) - 2*exp(2*x);
##interval = 1:0.001:5;
##n = 1.03;h1 = HermitePol(x1, f(x1), d(x1), n)
##H1 = HermitePol(x1, f(x1), d(x1), interval);
##e1 = abs(f(n)-h1)
##x2 = [1 1.05 1.07 1.11 1.13];
##h2 = HermitePol(x2, f(x2), d(x2), n)
##H2 = HermitePol(x2, f(x2), d(x2), interval);
##e2 = abs(f(n)-h2)
##hold on;
##plot(interval, f(interval));
##plot(interval, H1);
##plot(interval, H2);
