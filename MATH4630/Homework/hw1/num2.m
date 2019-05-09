% Liam Fruzyna
% MATH 4630
% HW 1 #2
% pg 50, example 5
clear all; close all
format compact

%% Input
N = 25;         % number of weeks
rr = 0.6;       % removal rate
a = 0.001407;   % disease rate
r(1) = 0;       % initial removed people
i(1) = 5;       % initial infected people
s(1) = 995;     % initial susceptible people

%% Calculation
for n = 1:(N-1)
    r(n+1) = r(n) + rr * i(n);
    i(n+1) = i(n) - rr * i(n) + a * i(n) * s(n);
    s(n+1) = s(n) - a * s(n) * i(n);
end
week = 1:N;

%% Output
week = 1:N;
plot(week, r)
xlabel('Time')
ylabel('Population')
title('Population vs Time')
hold on
plot(week, i)
plot(week, s)