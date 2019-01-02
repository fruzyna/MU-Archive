%Drug concentration fitting
clear all 
close all

%Model = y = xls(1)*t*exp(xls(2)*t)
% y = c1 * t * e^(c2t)
% ln(y) = ln(c1) + ln(t) + c2t
% ln(y) = k + ln(t) + c2t
% ln(y) - ln(t) = k + c2t

% y/e^(c2t)t = c1
% ln(y/c1t)/t = c2

y = [0 8 12.3 15.5 16.8 17.1 15.8 15.2 14]';

plot(t,y,'o')
axis([0 10 0 20]);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  Use transformed least squares to estimate xls

% Generate yfit for this approach using
t = 0:0.1:8;
t = [0:8]';

ln(y) - ln(t) = ones(length(t)))

% Plot the resulting fit
hold on
plot(t,yfit)





%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Use Gauss-Newton to determine the least square estimate of xls
x = [20 -1]';
t = [0:8]';

N = 10
for i=1:N
    r = y - x(1) .* t .* exp(x(2) .* t);
    Dr1 = t .* exp(x(2) .* t);
    Dr2 = x(1) .* t .* t .* exp(x(2) .* t);
    Dr = [Dr1, Dr2];
    DrTDr = Dr' * Dr;
    b = Dr' * r;
    v = b\DrTDr;
    x = x - v';
end
x

% Generate yfit2 for this approach using
t = 0:0.1:8;
ylong = x(1) .* t .* exp(x(2) .* t);

% Plot the resulting fit
hold on
plot(t,ylong,'k')


