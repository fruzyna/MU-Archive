% Liam Fruzyna
% MATH 4540
% Assignment 3

format long

% 5.3 1c) Use Romberg integration approximation R_55 to approximate the
% definite integral xe^x from 0 to 1. Compare with the correct integral and
% report the error.

f = @(x) x * exp(x);

syms x;
definite = int(f(x), [0 1])

approx = romberg(f, 5, 0, 1)
error = double(abs(definite - approx))

function [result] = romberg(f, n, x0, x1)
    h = (x1 - x0) ./ (2.^(0:n-1));
    results(1,1) = (x1 - x0) * (f(x0) + f(x1)) / 2;
    for i=2:n
        sub = 0;
        for j=1:2^(i-2)
            sub = sub + f(x0 + (2 * j - 1) * h(i));
        end
        results(i,1) = results(i-1,1) / 2 + h(i) * sub;
        for k=2:i
            results(i,k) = (4^(k-1) * results(i,k-1) - results(i-1,k-1)) / (4^(k-1) - 1);
        end
    end
    result = results(n,n);
end