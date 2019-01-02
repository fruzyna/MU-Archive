%Jacobi F18
clear all
close all

R1 = @(v) (5 + v)/3;  %2*v - 4; %
R2 = @(u) (4 + u)/2;  %3*u - 5;  %

uJ(1) = 0;
vJ(1) = 0;
uGS(1) = 0;
vGS(1) = 0;

N = 10;

for k = 1:N
uJ(k+1) = R1(vJ(k));
vJ(k+1) = R2(uJ(k));

uGS(k+1) = R1(vGS(k));
vGS(k+1) = R2(uGS(k+1));
end

errorJ = [uJ(k+1) - 2.8, vJ(k+1) - 3.4];
nerrorJ = max(abs(errorJ))
plot(uJ)
hold on
plot(vJ,'k')

figure

errorGS = [uGS(k+1) - 2.8, vGS(k+1) - 3.4];
nerrorGS = max(abs(errorGS))
plot(uGS)
hold on
plot(vGS,'k')

format compact
format long

uJ(k+1)
vJ(k+1)

uGS(k+1)
vGS(k+1)