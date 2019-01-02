library(astsa)

plot.ts(gnp)
plot.ts(log(gnp))

acf(gnp) # is not a stationary process, needs differencing

gnpdiff = diff(gnp)

# if it still wan't stationary
gnpdiff2 = diff(gnp, 2)

par(mfrow=c(2,1))
acf(gnpdiff) # moving average q = 3
pacf(gnpdiff) # autoregressive p = 2

fit = arima(gnp, order=c(2,1,3), method='CSS', include.mean=TRUE)
summary(fit)
fit

acf(fit$residuals)
qqnorm(fit$residuals)
qqline(fit$residuals)

Box.test(fit$residuals, type='Ljung', lag=20)