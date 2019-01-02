% Oil

clear all
close all

%Enter y and t data

y = [67.052  68.008 69.803 72.024 73.4 72.063 74.669 74.487 74.065 76.777]';

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
xls = ATA\ATb
%xls = A\

%x = linsolve(A,y);

%Construct and plot the model function

yfit = A * xls

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