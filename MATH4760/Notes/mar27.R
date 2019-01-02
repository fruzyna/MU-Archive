# March 27 In Class Notes

library(astsa)

# Old Way
plot.ts(varve)
plot.ts(log(varve))
acf2(log(varve))
acf2(diff(log(varve)))
fit = arima(log(varve), order=c(0, 1, 1)) # very small residuals 0, 1, 2 is better
acf2(fit$residuals)
Box.test(fit$residuals, type='Ljung', lag=20)

# New ARFIMA Way
library(fracdiff)
lvarve = log(varve) - mean(varve) # assumes mean is 0
varve.fd = fracdiff(lvarve, nar=0, nma=0, M=30)
varve.fd

fracdiff = diffseries(lvarve, varve.fd$d)
acf2(fracdiff)
Box.test(fracdiff, type='Ljung', lag=20)

plot.ts(nyse, ylab='NYSE Returns')
acf(nyse)

plot.ts(gnp)
lgnp = log(gnp)
acf2(lgnp)
difflgnp = diff(lgnp)
acf2(difflgnp)
fit = arima(lgnp, order=c(3,1,1))
Box.test(fit$residuals, type='Ljung', lag=20)
acf2(fit$residuals)
ressq = fit$residuals
acf2(ressq)
fit2 = garchFit(~arima(3,1) + garch(1,0), difflgnp)
summary(fit2)