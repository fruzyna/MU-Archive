% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 6.1 4c) Make a log-log plot of the error of Euler's Method at t=1 as a
% function of h = 0.1 x 2^-k for 0<=k<=5 for y' = 2(t + 1)y

syms y(t)
ySol(t) = dsolve(diff(y,t) == 2 * (t + 1) * y, y(0) == 1);
real = double(ySol(1))

f = @(t,y) 2 * (t + 1) * y;

error = zeros(1,5);
for k=0:5
    error(k+1) = euler(f, 0, 1, 0.1 * 2^(-k), 1) - real;
end
plot(error)

function [result] = euler(f, a, b, h, y0)
    result = 0;
    n = (b - a) / h;
    t = zeros(1, n+1);
    y = zeros(1, n+1);
    t(1) = a;
    y(1) = y0;
    for i=1:n
        t(i+1) = t(i) + h;
        result = y(i) + h * f(t(i),y(i));
        y(i+1) = result;
    end
end