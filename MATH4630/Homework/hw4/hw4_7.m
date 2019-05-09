% Determine the best method to use

lengths = [14.5 12.5 17.25 14.5 12.625 17.75 14.125 12.615];
girths  = [9.75 8.375 11.0 9.75 8.5 12.5 9.0 8.5];
weights = [27 17 41 26 17 49 23 16];

llg = lengths.^2 .* girths;
lgg = lengths .* girths.^2;

a4 = 0.0126;
a5 = 0.0125;
a6 = 0.0187;

method(a4, llg, weights, girths);
method(a5, llg, weights, girths);
method(a6, lgg, weights, girths);
hold off

function method(a, x, weights, girths)
    estW = a .* x;
    
    a
    sum((estW - weights).^2)
    max(abs(estW - weights))
    
    plot(sort(girths), sort(abs(estW - weights)))
    title('Residuals')
    xlabel('Girth')
    ylabel('Residual')
    hold on
end

function sorted = sort(vector)
    order = [2 5 8 7 1 4 3 6];
    sorted = vector;
    for i = 1:8
        sorted(i) = vector(order(i));
    end
end