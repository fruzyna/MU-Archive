% Liam Fruzyna
% MATH 4540
% Homework 1
% 9/12/2018

% Section 1.1 #2a
% Use the Bisection Method to find the root to eight correct decimal places
% for f(X) = x^5 + x = 1

% Formatting
format compact % limit white space
format long % lengthen decimals

% Variable setup
% f(x) = x^5 + x = 1
bisec = @(x) (x^5 + x - 1);
a = 0;
b = 1;
TOL = 0.000000005; % tolerance to 8 decimal places

% Run function to get result
results = bisecWhile(bisec, a, b, TOL);
final = results(end);

disp("Section 1.1 #2a");
disp(final);

% Calculate and plot error
for i=1:length(results)-1
    error(i) = abs(results(i) - final);
end
plot(error)

% Function to calculate root with bisection
function[results] = bisecWhile(bisec, a, b, TOL)
    iterations = 0;
    while (b - a) / 2 > TOL
        iterations = iterations + 1;
        c = (a + b) / 2;
        fa = bisec(a);
        fb = bisec(b);
        fc = bisec(c);
        results(iterations) = c;
        if fa*fc < 0
            b = c;
        else
            a = c;
        end
    end
end