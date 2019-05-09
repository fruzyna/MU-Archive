% constraints
bodyHours = 2400;
paintHours = 2500;
cBodyHours = 50;
tBodyHours = 50;
cPaintHours = 40;
tPaintHours = 60;

% determine max cars or trucks viable.
maxCars = min(bodyHours / cBodyHours, paintHours / cPaintHours)
maxTrucks = min(bodyHours / tBodyHours, paintHours / tPaintHours)

% function to calculate profit
profit = @(c, t) 3000 * c + 2000 * t;

max = [0 0];
% iterate through all possible combinations of cars and trucks
for cars = 0:maxCars
    for trucks = 0:maxTrucks
        % confirm it is a viable combination
        if bodyHours >= cBodyHours * cars + tBodyHours * trucks && paintHours >= cPaintHours * cars + tPaintHours * trucks
            % determine if the combination is better than any previous
            p = profit(cars, trucks);
            if p > profit(max(1), max(2))
                max = [cars, trucks];
            end
        end
    end
end

max
profit(max(1), max(2))