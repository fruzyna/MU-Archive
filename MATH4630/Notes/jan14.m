% Calculation of monthly payment to pay off a 30-year $100,000 student loan
% with a montly interest rate of 0.5%
clear all; close all
format compact

%% Input
N = 12*15;    % number of payments
r = 0.003;   % monthly interest rate
b(1) = 87878;  % initial balance
p = 635;    %Guess at monthly payment

%% Calculation
interest = 0;
for n = 1:(N-1)
    interest = interest + b(n) * r;
    b(n+1) = (1+r)*b(n) - p;
end
month = 1:N;

%% Output
month = 1:N;
plot(month,b)
pfinal = b(N)
total_payment = p*N
interest

%% After 8 years
% Interest: 44,875
% Remaining: 87,878
% a) Continue as is 0.5% @ 600
%   Interest: 70,667
% b) 20 years at 0.33% + 2500 @ 532
%   Interest: 39,317 + 2,500
% c) 15 years at 0.3% + 2500 @ 635
%   Interest: 25,837 + 2,500
