% Liam Fruzyna
% MATH 4540
% Homework 1
% 9/12/2018

% Section 1.2 #2a
% Apply Fixed-Point Iteration to find the solution of f(X) = x^5 + x = 1 to
% 8 decimal places

% Formatting
format compact % limit white space
format long % lengthen decimals

% Variable setup
% f(x) = x^5 + x = 1 or 1 - x^5 = x
gx = @(x) (1 - x^5);
x0 = 0.5;
TOL = 0.00000001; % tolerance to 8 decimal places

% Run function to get result
results = fixedPointIteration(gx, x0, TOL);
final = results(end);

disp("Section 1.2 #2a");
disp(final);

% Calculate and plot error
for i=1:length(results)-1
    error(i) = abs(results(i) - final);
end
plot(error)

% Function to calculate root with fixed point iteration
function[results] = fixedPointIteration(g, x0, TOL)
    i = 1;
    x(i) = x0;
    done = false;
    while ~done
        i = i + 1;
        x(i) = g(x(i-1));
        
        if abs(x(i) - x(i-1)) <= TOL
            done = true;
        end
    end
    results = x;
end