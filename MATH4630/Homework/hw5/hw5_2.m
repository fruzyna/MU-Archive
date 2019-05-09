% Liam Fruzyna
% MATH 4630
% Assignment 5 (#4)

% constraints lhs (<=)
A = [50 50   % 50c + 50t <= 2400
     40 60   % 40c + 40t <= 2500
     -1  0   % c >= 0 or -c <= 0
      0 -1]; % t >= 0 or -t <= 0

% constraints (<=) rhs
b = [2400 2500 0 0];

% objective function
f = [-3000 -2000]; % min(-3000c - 2000t)

% variable just for reference
% x = [c t];

% solve linear programming problem
x = linprog(f, A, b)