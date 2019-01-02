% 5.3 3c


% 6.2 3c) For y' = 2(t + 1)y (log-log) plot the global truncation error of the
% explicit trapezoid method at t=1 as a function of h = 0.1x2^(-k) for
% 0<=k<=5.

f = @(t, w) 2 * (t + 1) * w;

gte = zeros(1, 5);
for k=0:5
    h = 0.1 * 2^(-k);
    w = euler(f, 0, 1, h, 1, 1);
    y = int(f, [0 1]);
    gte(k) = abs(w - y);
end
loglog(gte)

function [w] = euler(f, a, b, h, y0, n)
    t = zeros(1, n+1);
    w = zeros(1, n+1);
    t(1) = a;
    w(1) = y0;
    for i=1:n
        t(i+1) = t(i) + h;
        w(i+1) = w(i) + (h / 2) * (f(t(i),w(i)) + f(t(i)+h,w(i)+h*f(t(i),w(i))));
    end
end

% 6.2 4c) For y' = 2(t + 1)y plot the global truncation error of the 2nd
% order taylor method at t=1 as a function of j = 0.1x2^(-k) for 0<=k<=5

f = @(t, w) 2 * (t + 1) * w;

w = zeros(1, 5);
y = zeros(1, 5);
for k=0:5
    h = 0.1 * 2^(-k);
    w(k) = euler(f, 0, 1, h, 1, 1);
    y(k) = int(f, [0 1]);
end
plot(y, w)

function [w] = euler(f, a, b, h, y0, n)
    t = zeros(1, n+1);
    w = zeros(1, n+1);
    t(1) = a;
    w(1) = y0;
    for i=1:n
        t(i+1) = t(i) + h;
        w(i+1) = w(i) + h * f(t(i),w(i)) + (h^2/2) * f(t(i),w(i));
    end
end