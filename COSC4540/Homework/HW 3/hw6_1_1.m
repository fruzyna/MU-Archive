% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 6.1 1c) Apply Euler's method with step size h = 0.1 from 0 to 1 to the
% initial value problem y' = 2(t + 1)y. Print a table of the t values,
% Euler approximation, and the error at each step.

f = @(t,y) 2 * (t + 1) * y;

syms y(t)
ySol(t) = dsolve(diff(y,t) == 2 * (t + 1) * y, y(0) == 1);
real = double(ySol(1));

euler(f, 0, 1, 0.1, 1, real)

function [t, y] = euler(f, a, b, h, y0, real)
    n = (b - a) / h;
    t = zeros(1, n+1);
    y = zeros(1, n+1);
    e = zeros(1, n+1);
    t(1) = a;
    y(1) = y0;
    for i=1:n
        t(i+1) = t(i) + h;
        y(i+1) = y(i) + h * f(t(i), y(i));
        e(i+1) = double(abs(y(i+1) - real));
    end
    plot(t,y);
    e
end