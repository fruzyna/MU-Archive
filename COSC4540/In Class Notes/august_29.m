% August 29th, 2018 In Class Notes
% COSC 4540

% Formatting
format compact % limit white space
format long % lengthen decimals

% Bisection Method
% f(x) = x^3 - 9
bisec = @(x) (x^3 - 9);
deriv = @(x) (3 * x^2);

% variable initialization
a = 2; % start
b = 3; % end
n = 3; % number of iterations
tol = 0.000005; % tolerance

% get result and display
result = bisecFor(bisec, a, b, n);
disp('bisection n = 3 result');
disp(result);

result = bisecWhile(bisec, a, b, tol);
disp('bisection unlimited result');
disp(result);

result = newtonsFor(bisec, deriv, a, n);
disp('newtons result')
disp(result)

function[result] = bisecFor(bisec, a, b, n) 
    for k=1:n
        fa = bisec(a);
        fb = bisec(b);

        c = (a + b) / 2;
        fc = bisec(c);
        if fa * fc < 0
            b = c;
        else
            a = c;
        end
    end

    result = (a + b) / 2;
end

function[result] = bisecWhile(bisec, a, b, TOL)
    iterations = 0;
    while (b - a) / 2 > TOL
        iterations = iterations + 1;
        c = (a + b) / 2;
        fa = bisec(a);
        fb = bisec(b);
        fc = bisec(c);
        if fa*fc < 0
            b = c;
        else
            a = c;
        end
    end
    result = (a + b) / 2;
end

function[result] = newtonsFor(func, deriv, x, n)
    for k=1:n
        x = x - (func(x) / deriv(x));
    end
    result = x;
end