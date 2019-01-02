# February 20 In Class Notes
# Page 78 of book

library(astsa)

# Simulate ARIMA on 1000 values
# For X_t = 0.9X_{t-1} + Z_t
x1 = arima.sim(list(order=c(1,0,0), ar=0.9), n=1000)
# For X_t = -0.9X_{t-1} + Z_t
x2 = arima.sim(list(order=c(1,0,0), ar=-0.9), n=1000)

# Create 1 plot with 2 rows
par(mfrow=c(2,1))

# Plot both simulations
plot.ts(x1, ylab='X1', main=expression(AR(1)~~~phi==0.9))
plot.ts(x2, ylab='X2', main=expression(AR(1)~~~phi==-0.9))

# Again with phi = 0
x3 = arima.sim(list(order=c(1,0,0), ar=0), n=1000)
x4 = arima.sim(list(order=c(1,0,0), ar=-0), n=1000)
plot.ts(x3, ylab='X3', main=expression(AR(1)~~~phi==0))
plot.ts(x4, ylab='X4', main=expression(AR(1)~~~phi==-0))

# Again with phi = +1
x5 = arima.sim(list(order=c(0,1,0)), n=1000)
plot.ts(x5, ylab='X5', main=expression(AR(1)~~~phi==1))
plot.ts(x5, ylab='X5', main=expression(AR(1)~~~phi==1))

# Plot Autocorrelation Function of first set
acf(x1)
acf(x2)

# For Moving Average
x6 = arima.sim(list(order=c(0,0,1), ma=0.9), n=1000)
x7 = arima.sim(list(order=c(0,0,1), ma=-0.9), n=1000)
plot.ts(x6, ylab='X6', main=expression(MA(1)~~~phi==0.9))
plot.ts(x7, ylab='X7', main=expression(MA(1)~~~phi==-0.9))

# Plot Autocorrelation Function
acf(x6)
acf(x7)

# Again for phi = +/-10.9
x8 = arima.sim(list(order=c(0,0,1), ma=10.9), n=1000)
x9 = arima.sim(list(order=c(0,0,1), ma=-10.9), n=1000)
plot.ts(x8, ylab='X8', main=expression(MA(1)~~~phi==10.9))
plot.ts(x9, ylab='X9', main=expression(MA(1)~~~phi==-10.9))
acf(x8)
acf(x9)