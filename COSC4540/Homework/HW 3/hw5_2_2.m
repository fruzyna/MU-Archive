% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 5.2 2c) Apply the composite Simpson's rule to the integral xe^x from 0 to 1

f = @(x) x * exp(x);

syms x;
definite = int(f(x), [0 1])

approx = simpsons(f, 16, 0, 1)
error = double(abs(definite - approx))

approx = simpsons(f, 32, 0, 1)
error = double(abs(definite - approx))

function[result] = simpsons(f, m, x0, x2)
    h = (x2 - x0) / m;
    even = 0;
    for i=1:m/2-1
        x = x0 + 2 * i * h;
        even = even + f(x);
    end
    odd = 0;
    for i=1:m/2
        x = x0 + (2 * i - 1) * h;
        odd = odd + f(x);
    end
    result = (h / 3) * (f(x0) + f(x2) + 2 * even + 4 * odd);
end