% Liam Fruzyna
% COSC 4540
% Homework 4 #1

clear all
close all

% initialize functions
R1 = @(v) (5 + v)/3;  %2*v - 4; %
R2 = @(u) (4 + u)/2;  %3*u - 5;  %

% initialize arrays
uJ(1) = 0;
vJ(1) = 0;
uGS(1) = 0;
vGS(1) = 0;

% number of iterations
N = 10;

% iterate N times
for k = 1:N
    % run for jacobi
    uJ(k+1) = R1(vJ(k));
    vJ(k+1) = R2(uJ(k));

    % run for gauss-seidel
    uGS(k+1) = R1(vGS(k));
    vGS(k+1) = R2(uGS(k+1));
end

% calculate error and plot values for jacobi
errorJ = [uJ(k+1) - 2.8, vJ(k+1) - 3.4];
nerrorJ = max(abs(errorJ))
plot(uJ)
hold on
plot(vJ,'k')

figure

% calculate error and plot values for gauss-seidel
errorGS = [uGS(k+1) - 2.8, vGS(k+1) - 3.4];
nerrorGS = max(abs(errorGS))
plot(uGS)
hold on
plot(vGS,'k')

% display final results
format compact
format long

uJNorm = uJ(k+1)
vJNorm = vJ(k+1)

uGSNorm = uGS(k+1)
vGSNorm = vGS(k+1)