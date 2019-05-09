% Dermine the Chebyshev estimate of k and plot

lengths = [14.5 12.5 17.25 14.5 12.625 17.75 14.125 12.615];
girths  = [9.75 8.375 11.0 9.75 8.5 12.5 9.0 8.5];
weights = [27 17 41 26 17 49 23 16];

x = lengths.^2 .* girths;
y = weights;

a = 0.01;
b = 0.02;
k = 0.015;
for i = 1:100
    adeltas = abs((a .* x) - weights);
    bdeltas = abs((b .* x) - weights);
    if max(adeltas) < max(bdeltas)
        b = k;
    else
        a = k;
    end
    k = a + (b - a)/2;
end

k

estW = k .* x;

scatter(estW, weights);
title('Approximation of Weight of Fish (Chebyshev Method)')
xlabel('Estimated Weight of Fish')
ylabel('Actual Weight of Fish')
lsline