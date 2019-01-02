% Liam Fruzyna
% COSC 4540
% Homework 5
% 3.2 CP #3

clear all
close all

%Enter y and t data

y = [67.052 68.008 69.803 72.024]';

N = length(y);
t = [1:N]; 
plot(t,y,'*')

%Construct the A matrix
for i = 1:N
    A(:,i) = t.^(i-1);
end

%Solve the normal equations
ATA = A'*A;
ATb = A'*y;
xls = ATA\ATb;
%xls = A\

%x = linsolve(A,y);

%Construct and plot the model function

yfit = A * xls;

%Calculate RMSE

hold on
plot(t,yfit,'r')
figure

t = [1:0.01:N];

%Construct the A matrix
for i = 1:N
    B(:,i) = t.^(i-1);
end

Longyfit = B * xls;
plot(t,Longyfit,'k')

ypred = 0;
xpred = 5;
for i=1:N
    ypred = xls(i) * xpred^(i-1) + ypred;
end
ypred

fit = polyfit([1:0.01:N]', Longyfit, 3);
fit(4) + fit(3) * 5 + fit(2) * 25 + fit(1) * 125