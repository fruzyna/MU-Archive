# Liam Fruzyna
# MATH 4760
# Assignment 3

# 3 Simulate 500 data points of the AR(3) process of 2c, draw the times series plot and its ACF and PACF
# X_t = 0.8X_t - 0.5X_t-2 + 0.4X_t-3 + Z_t
x = arima.sim(list(order=c(3,0,0), ar=c(0.8, 0, -0.5, 0.4)), n=500) # mostly done just dont know how to deal with 0.8X_t
par(mfrow=c(3,1))
plot.ts(x)
acf(x)
pacf(x)

# 5 Simulate 500 data points of the MA(3) process of 4c, draw the times series plot and its ACF and PACF
# X_t = Z_t - 0.8X_t-1 + 1.5X_t-2 - 1.8X_t-3
x = arima.sim(list(order=c(0,0,3), ma=c(-0.8, 1.5, -1.8)), n=500)
par(mfrow=c(2,1))
plot.ts(x)
acf(x)

# 6 Simulate 500 data points of the ARIMA process
# X_t = 0.8X_t-2 + 0.4X_t-3 + Z_t - 0.8Z_t-1 + 1.5Z_t-2 - 1.8Z_t-3
x = arima.sim(list(order=c(3,0,3), ar=c(0, 0.8, 0.4), ma=c(-0.8, 1.5, -1.8)), n=500)
par(mfrow=c(3,1))
plot.ts(x)
acf(x)
pacf(x)