% Calculation of population size over 20 years with an initial population
% of 10 and a net population rate of 100%
clear all; close all
format compact
format long g

%% Input
N = 20;      % number of time steps
r = 1 + 0.1;    % "growth" rate
a(1) = 10;   % initial value

%% Calculation 
for n = 1:(N-1)
    a(n+1) = r*a(n); %Numerically calculate the value of a at each time point
end

%% Output
time = 1:N; %Create a time vector for plotting
plot(time,a,'*r') % Plot as red stars
afinal = a(N)
aequilibrium = r^100 *a(1)