```matlab
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
```

![1.1](/home/mail929/Code/Class/COSC-4540/Homework/1.1.png)

The graph shows that the method converges quadratically.



```matlab
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
```

The method failed to converge so there is no graph to show. This is because the derivative is not within 1.



```matlab
% Section 1.4 #2a
% Apply Newton's method to find the solution of f(X) = x^5 + x = 1 to
% 8 decimal places

% Formatting
format compact % limit white space
format long % lengthen decimals

% Variable setup
% f(x) = x^5 + x = 1
fx = @(x) (x^5 + x - 1);
fpx = @(x) (5*x^4 + 1);
x0 = 0.5;
TOL = 0.000000005; % tolerance to 8 decimal places

% Run function to get result
results = newtons(fx, fpx, x0, TOL);
final = results(end);

disp("Section 1.4 #2a");
disp(final);

% Calculate and plot error
for i=1:length(results)-1
    error(i) = abs(results(i) - final);
end
plot(error)

% Function to calculate root with Newton's method
function[results] = newtons(f, fp, x0, TOL)
    i = 1;
    x(i) = x0;
    done = false;
    while ~done
        i = i + 1;
        x(i) = x(i-1) - f(x(i-1)) / (fp(x(i-1)));
        
        if abs(x(i) - x(i-1)) <= TOL
            done = true;
        end
    end
    results = x;
end
```

![1.4](/home/mail929/Code/Class/COSC-4540/Homework/1.4.png)

The method converges quadratically.



```matlab
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
```

![1.5](/home/mail929/Code/Class/COSC-4540/Homework/1.5.png)

The method converges quadratically.