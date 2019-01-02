format long g

% compute each iteration
results = zeros(6, 3);
odds = 0.5;
results(1, :) = [0 15 0.625];
results(2, :) = randWalk(100, odds);
results(3, :) = randWalk(500, odds);
results(4, :) = randWalk(1000, odds);
results(5, :) = randWalk(5000, odds);
results(6, :) = randWalk(10000, odds);
results

odds = 0.46732;
odds = 0.6;
results(1, :) = [0 15 0.625];
results(2, :) = randWalk(100, odds);
results(3, :) = randWalk(500, odds);
results(4, :) = randWalk(1000, odds);
results(5, :) = randWalk(5000, odds);
results(6, :) = randWalk(10000, odds);
results

% function to compute random walk
function[results] = randWalk(n, up)
    % constants
    top = 3;
    bottom = -5;
    avgSteps = 0;
    prob = 0;
    
    % run 4 times
    for j=1:4
        % compute averages from running n times
        breakTop = 0;
        steps = 0;
        for i=1:n
            y = 0;
            % run until it hits the top/bottom
            while y ~= top && y ~= bottom
                % randomly step up for down
                odds = rand(1);
                steps = steps + 1;
                if odds < up
                    y = y + 1;
                else
                    y = y - 1;
                end
            end
            % count breaches at top
            if y == top
                breakTop = breakTop + 1;
            end
        end
        % compute average steps and probability
        avgSteps = avgSteps + steps / n;
        prob = prob + breakTop / n;
    end
    % compute averages for all 4 runs
    avgSteps = avgSteps / 4;
    prob = prob / 4;
    results = [n avgSteps prob];
end