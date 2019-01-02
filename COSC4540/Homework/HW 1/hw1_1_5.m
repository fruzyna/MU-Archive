% Liam Fruzyna
% MATH 4540
% Homework 1
% 9/12/2018

% Section 1.5 #2a
% Apply the secant method to find the solution of f(X) = x^5 + x = 1 to
% 8 decimal places

% Formatting
format compact % limit white space
format long % lengthen decimals

% Variable setup
% f(x) = x^5 + x = 1
fx = @(x) (x^5 + x - 1);
x0 = 0.0;
x1 = 0.5;
TOL = 0.000000005; % tolerance to 8 decimal places

% Run function to get result
results = secant(fx, x0, x1, TOL);
final = results(end);

disp("Section 1.2 #2a");
disp(final);

% Calculate and plot error
for i=1:length(results)-1
    error(i) = abs(results(i) - final);
end
plot(error)

% Function to calculate root with secant method
function[results] = secant(f, x0, x1, TOL)
    x(1) = x0;
    x(2) = x1;
    i = 2;
    while abs(x(i) - x(i-1)) > TOL
        i = i + 1;
        x(i) = x(i-1) - ((f(x(i-1)) * (x(i-1) - x(i-2))) / (f(x(i-1)) - f(x(i-2))));
    end
    results = x;
end