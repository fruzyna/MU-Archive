# February 22 In Class Notes

# Find roots of phi(z) = z_0 + -1.5 z_1 + 0.75 z_2
phicoeff = c(1, -1.5, 0.75)
root = polyroot(phicoeff)
abs(root)
# Is not causal because abs(root) = |z| > 1

# Simulate X_t = 1.5X_t-1 - 0.75X_t-2 + Z, Autoregressive only
x = arima.sim(list(order=c(2,0,0), ar=c(1.5, -0.75)), n=500)
par(mfrow=c(3,1))
plot.ts(x)
acf(x)

# Partial ACF
pacf(x) # cuts off at order p=2

# Simulate X_t = 1.5Z_t-1 - 0.75Z_t-2 + Z, Moving Average Only
x = arima.sim(list(order=c(0, 0, 2), ma=c(1.5, -0.75)), n=1000)
plot.ts(x)
acf(x)
pacf(x)

# Both AR and MA
x = arima.sim(list(order=c(2, 0, 2), ar=c(0.7, 0.2), ma=c(1.5, -0.75)), n=1000)
plot.ts(x)
acf(x)
pacf(x)