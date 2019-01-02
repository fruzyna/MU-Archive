% September 26 In Class Notes
% Euler's Method

% population function
%f = @(tp, wp) (0.5 * (10 - wp) *  wp);
f = @(tp, wp) (2 * (tp + 1) *  wp);

% parameters
h = 0.1; % decreasing value increases percision
t = 0:h:1;
N = length(t);

% initial condition (2 people)
y0 = 2;
w = zeros(N, 1);
w(1) = y0;

% approximate value at each time
for i=1:(N-1)
    w(i+1) = w(i) + h * f(t(i), w(i));
end

% plot
plot(t, w);

% actual values
y = 2 * exp(t.*t + 2.*t);
hold on
plot(t, y, 'k');

% calulate error
error = abs(w-y);
%errorTab = [t', w', y', error'];
%disp(table);