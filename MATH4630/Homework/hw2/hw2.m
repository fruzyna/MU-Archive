% Liam Fruzyna
% MATH 4630
% Homework 2
% Part 5

close all
clear all

% Length to calculate to
L = 40;

% m < P0 < M (P0 = 150)
P = euler(150, L);
plot(1:L,P);
hold on

% P0 < m (P0 = 99)
P = euler(99, L);
plot(1:L,P);
hold on

% P0 > M (P0 = 250)
P = euler(250, L);
plot(1:L,P);

% Plot lines at M and m
yline(200, '--');
yline(100, '--');

% Add axis labels
xlabel('Time')
ylabel('Population')
hold off

% Function to calculate P using Euler's
function P = euler(P0, l)
    %input variables
    k = 0.001;
    M = 200;
    m = 100;
    
    % Differential equation is dP/dt = k(M-P)(P-m)
    f = @(p) k*(M - p)*(p - m);

    deltat = 1;     % Change in time per step
    L = l - 1;      % Length of t and P
    P = zeros(1,L); % Empty vector for P
    P(1)= P0;       % Set first element of y vector to y0.

    % Do Euler's L times
    for i = 1:L          
        P(i+1) = P(i) + f(P(i))*deltat;
    end
end
