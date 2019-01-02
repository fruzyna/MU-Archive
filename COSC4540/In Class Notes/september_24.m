%Romberg Integration
clear all
close all
format long
format compact

% Function
f = @(x) log(x);

% Parameters
a = 1; b = 2;
N = 8;
h = (b - a);
R(1,1) = h*(f(a) + f(b))/2;

% Calculate Romberg Trapezoidal Integration Formula
for j = 2:N
    h = h/2;
    subtotal = 0;
    upperlimit = 2^(j-2);
    for i = 1:upperlimit
        subtotal = subtotal + f(a + (2*i - 1)*h);
    end
    R(j,1) = R(j-1,1)/2 + h*subtotal;
    for k = 2:j
        R(j,k) = (4^(k-1)*R(j,k-1) - R(j-1,k-1))/(4^(k-1) - 1);
    end
end
disp(R);

% Calculate to a desired accuracy
TOL = 0.000001;
ERROR = 1;

j = 1;
while ERROR > TOL
    j = j + 1;
    h = h/2;
    subtotal = 0;
    upperlimit = 2^(j-2);
    for i = 1:upperlimit
        subtotal = subtotal + f(a + (2*i - 1)*h);
    end
    R(j,1) = R(j-1,1)/2 + h*subtotal;
    for k = 2:j
        R(j,k) = (4^(k-1)*R(j,k-1) - R(j-1,k-1))/(4^(k-1) - 1);
    end
    ERROR = abs(R(j,j) - R(j-1, j-1));
end
disp(R);
disp(j);
disp("iterations");