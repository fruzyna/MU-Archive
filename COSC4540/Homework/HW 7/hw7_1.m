% 1) Find the area in the 1st quadrant bounded by x^3 and 2x-x^2
%    using n = 10^i for i = 1:6

% min/max n
na = 2;
nb = 6;

% bounds
a = 0;
b = 1;

% functions
f = @(x) (x^3);
g = @(x) (2*x - x^2);
m = max([f(a), f(b), g(a), g(b)]);

% run for every n value
for j=na:nb
    n = 10^j

    % shoot n times
    between = 0;
    for i=1:n
        x = rand(1) / (b - a) + a;
        y = rand(1) * m;
        if y < f(x) && y > g(x) || y > f(x) && y < g(x)
            between = between + 1;
        end
    end

    % compute area as proportion of hits
    hitRatio = between / n;
    area = (b - a) * m;
    areaUnder = hitRatio * area
end