% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 6.2 4c) For y' = 2(t + 1)y plot the global truncation error of the 2nd
% order taylor method at t=1 as a function of j = 0.1x2^(-k) for 0<=k<=5

f = @(t, w) 2 * (t + 1) * w;
fp = @(t, w) 2 * w + 2 * (t + 1) * 2 * (t + 1) * w;

hs = zeros(1, 6);
gte = zeros(1, 6);
for k=0:5
    h = 0.1 * 2^(-k);
    hs(k+1) = h;
    w = euler(f, fp, 0, 1, h, 1, 1);
    
    syms y(t)
    ySol(t) = dsolve(diff(y,t) == 2 * (t + 1) * y, y(0) == 1);
    gte(k+1) = abs(w - double(ySol(1)));
end
loglog(hs, gte)

function [result] = euler(f, fp, a, b, h, y0, n)
    t = zeros(1, n+1);
    w = zeros(1, n+1);
    t(1) = a;
    w(1) = y0;
    result = 0;
    for i=1:n
        t(i+1) = t(i) + h;
        result = w(i) + h * f(t(i),w(i)) + (h^2/2) * fp(t(i),w(i));
        w(i+1) = result;
    end
end