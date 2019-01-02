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

% tolerance
TOL = 10^(-6);

% iterate for jacobi until error reaches tolerance
nerrorJ = 1;
k = 1;
while nerrorJ > TOL
    uJ(k+1) = R1(vJ(k));
    vJ(k+1) = R2(uJ(k));

    % calculate running error
    errorJ = [uJ(k+1) - 2.8, vJ(k+1) - 3.4];
    nerrorJ = max(abs(errorJ));
    k = k+1;
end
k = k-1;
kJ = k;

% display final error and plot results
nerrorJ
plot(uJ)
hold on
plot(vJ,'k')

figure

% iterate for gauss-seidel until error reaches tolerance
nerrorGS = 1;
k = 1;
while nerrorGS > TOL
    uGS(k+1) = R1(vGS(k));
    vGS(k+1) = R2(uGS(k+1));

    % calculate running error
    errorGS = [uGS(k+1) - 2.8, vGS(k+1) - 3.4];
    nerrorGS = max(abs(errorGS));
    k = k+1;
end
k = k-1;
kGS = k;

% display final error and plot results
nerrorGS
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