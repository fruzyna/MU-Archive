# February 27 In Class Notes

# PACF for ARIMA, AR only
x = arima.sim(list(order=c(2,0,0), ar=c(0.7,0.2)), n=1000)
par(mfrow=c(3,1))
plot.ts(x)
acf(x)
pacf(x) # cuts off after h = p = 2

# MA only
y = arima.sim(list(order=c(0,0,2), ma=c(1.7,1.2)), n=1000)
par(mfrow=c(3,1))
plot.ts(y)
acf(y) # cuts off after lag q = 2
pacf(y)

# SOI (Ocean Temperature) Dataset
library(astsa)
plot.ts(soi)
acf(soi) # has oscillating behavior, never cuts off: not moving average
pacf(soi) # cuts off after 1/12

# Recruitment (number of new fish), pairs with SOI
plot.ts(rec)
acf(rec)
pacf(rec)

# Make a model for the dataset
arfit = ar.ols(rec, order=2, demean=FALSE, intercept=TRUE)
arfit

# Predict the next 20 measurements
fore = predict(arfit, n.ahead=20)

# Plot the existing values and the predicted values
ts.plot(rec, fore$pred, col=1:2, xlim=c(1980,1990), ylab='Recruitment')
lines(fore$pred, type='p', col=2) # draw the points on the predicted line
lines(fore$pred + fore$se, lty='dashed', col=4) # draw dashed lines to show the standard error
lines(fore$pred - fore$se, lty='dashed', col=4)