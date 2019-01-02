# March 8 In Class Notes

library(astsa)

# Determine arima model
plot.ts(gnp)
acf(gnp)
gnp2 = log(gnp)
plot.ts(gnp2)
acf2(gnp2)
diffgnp = diff(gnp2)
acf2(diffgnp)
fit = arima(gnp2, order=c(1, 2, 3), method="ML", include.mean=TRUE)

acf2(fit$residuals)
Box.test(fit$residuals, type='Ljung', lag=20)

fit

# Attempt to forcast next 20 values
fore = predict(fit, n.ahead=20)

# Plot the predicted points on the existing data
ts.plot(gnp, exp(fore$pred), col=1:2, xlim=c(1980, 2010), ylab='gnp')
lines(fore$pred, type='p', col=2)
lines(exp(fore$pred + fore$se), lty='dashed', col=4)
lines(exp(fore$pred - fore$se), lty='dashed', col=4)