Liam Fruzyna

```matlab
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Use Gauss-Newton to determine the least square estimate of xls

% Generate yfit2 for this approach using
% t = 0:0.1:8

% Plot the resulting fit
%hold on
%plot(t,yfit2,'k')

clear all 
close all
format compact

model = @(ti,x1,x2) x1*ti*exp(x2*ti); %define the model fn
derivx1 = @(ti,x1,x2) ti*exp(x2*ti); %define deriv wrt x1
derivx2 = @(ti,x1,x2) x1*ti*ti*exp(x2*ti); %define deriv wrt x2

t = [1:8]';  %t data
y = [8 12.3 15.5 16.8 17.1 15.8 15.2 14]'; %ydata
M = length(t);

plot(t,y,'o') %plot the data
hold on

xls(1) = 8; %Initial guesses for the parameters
xls(2) = 1;
xls = xls';

N = 50; %Number of iterations in Gauss-Newton

for i = 1:N
    clear r;
    for j = 1:M % create the residual vector and the cols of Dr
     r(j) =  model(t(j),xls(1),xls(2)) - y(j);
     col1(j) = derivx1(t(j),xls(1),xls(2));
     col2(j) = derivx2(t(j),xls(1),xls(2));
    end
    r = r';
    Dr = [col1', col2']; %Create the Jacobian matrix Dr
    DrTDr = Dr'*Dr; 
    DrTr = Dr'*r;
    v = DrTDr\DrTr; %Compute the update which satisfies (Dr'Dr)v = (Dr')r
    xls = xls - v; %Update xls
end

for j = 1:M %Create the yfit vector
    yfit(j) = model(t(j),xls(1),xls(2));
end

tlong = [0:0.1:10]; %Create a t vector with many points
for j = 1:length(tlong) %Create the yfit vector
    yfitlong(j) = model(tlong(j),xls(1),xls(2));
end

plot(tlong,yfitlong,'k') %Plot yfit on top of data

%%%Output
xls
RMSE = r'*r  % Compute RMSE
```

![](/home/mail929/Code/Class/COSC-4540/Homework/HW 6/hw6.png)

```matlab
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  Use transformed least squares to estimate xls

% Generate yfit for this approach using
% t = 0:0.1:8

% Plot the resulting fit
%hold on
%plot(t,yfit)

t_trans = sqrt(t);
y_trans = sqrt(y);
plot(t, y_trans, 'o')

a1 = ones(length(t), 1);
a2 = t;
a = [a1, a2];

aTa = a' * a;
aTb = a' * y_trans;
x = aTa\aTb;

for i=1:length(t)
    line(i) = x(1) + x(2) * i;
end
plot(t, line)
```

