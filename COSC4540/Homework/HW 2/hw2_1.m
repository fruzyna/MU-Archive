% Liam Fruzyna
% MATH 4540
% Assignment 2
% Computer Problem 1
format long

% Make a table of the error of this three-point difference method for f'(0) where f(x) = sinx - cosx with h = 10^-1, 10^-2, ..., 10^-12. 
% Plot your results.
% Does the error correspond to the theoretical expectations?

f = @(x) (sin(x) - cos(x));
h = @(i) (10^(-i));
e3 = @(h) ((h^2/6)*1);
error3 = zeros(11, 1);

for i=1:11
    error3(i) = e3(h(i)*1);
end

disp(error3);
plot(error3);
figure;

% Computer Problem 2

% Repeat for two-point difference

e2 = @(h) ((h/2)*1);
error2 = zeros(11, 1);

for i=1:11
    error2(i) = e2(h(i)*1);
end

disp(error2);
plot(error2);