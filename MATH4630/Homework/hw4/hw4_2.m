% Plot W = l^2 * g to approximate the weight of a fish

lengths = [14.5 12.5 17.25 14.5 12.625 17.75 14.125 12.615];
girths  = [9.75 8.375 11.0 9.75 8.5 12.5 9.0 8.5];
weights = [27 17 41 26 17 49 23 16];

lgg = lengths .* girths.^2;
llg = lengths.^2 .* girths;

scatter(lgg, weights, "r")
hold on
scatter(llg, weights, "b")
lsline

xy = llg .* weights;
x2 = llg.^2;
m = 8;

a = sum(xy) / sum(x2)