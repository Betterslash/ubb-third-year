%% Ex 1 : 
x = [0, pi / 2, pi, (3 * pi) / 2, 2 * pi]; #nodes
y = sin(x);


#a)
t = spline(x, [1, y, 1]);
clamped = ppval(t, pi / 4)

t1 = spline(x, y);
natural = ppval(t1, pi / 4)

interval = linspace(0,2*pi,50);


#b)
hold on;
plot(interval, sin(interval));
xx = ppval(t, interval);
plot(interval, xx);
yy = ppval(t1, interval);
plot(interval, yy);


%%Ex 2 :
[x,y] = ginput(6);
a = min(x);
b = max(x);

t = linspace(a,b,50);

pol = spline(x,y);

hold on

plot(x, y, '*b')
plot(t, ppval(pol, t), 'g')

hold off

%%Ex 3 :
%%where spline of 3 parameters is actually ppval of spline in the third parameter
x = [0 3 5 8 13];
f = [0 225 383 623 993];
f1 = [75 77 80 74 72];
distance = spline(x,[0 f 0],10)
speed = spline(x,[0 f1 0],10)