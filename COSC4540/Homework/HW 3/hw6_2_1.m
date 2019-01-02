% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 6.2 1c) Apply the explicit trapezoid method on a grid of step size h=0.1
% from 0 to 1 to the initial value problem y' = 2(t + 1)y. Print a table of
% the t values, approximations, and global truncation error at each step.

f = @(t, w) 2 * (t + 1) * w;

euler(f, 0, 1, 0.1, 1);

function [t, w] = euler(f, a, b, h, y0)
    n = (b - a) / h;
    t = zeros(1, n+1);
    w = zeros(1, n+1);
    t(1) = a;
    w(1) = y0;
    for i=1:n
        t(i+1) = t(i) + h;
        w(i+1) = w(i) + (h / 2) * (f(t(i),w(i)) + f(t(i)+h,w(i)+h*f(t(i),w(i))));

        syms y(s)
        ySol(s) = dsolve(diff(y,s) == 2 * (s + 1) * y, y(0) == 1);
        error(i) = abs(double(ySol(t(i+1)) - w(i+1)));
    end
    t
    w
    error
    plot(t,w);
end