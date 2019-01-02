# April 10 In Class Notes

library(fracdiff)
library(astsa)

lvarve = log(varve) - mean(log(varve))
varve.fd = fracdiff(lvarve, nar=0, nma=0, M=30)
varve.fd

fracdiff = diffseries(lvarve, varve.fd$d)
acf2(fracdiff)
Box.test(fracdiff, type='Ljung', lag=20)


x = abs(nyse) - mean(abs(nyse))
fracx = fracdiff(x, nvar=0, nma=0, M=30)
fracx 


# Fitting the variance with \alpha_0 + \alpha_1 \sigma_t-1 ^2 + z_t ^2 + \theta z_t-1 ^2
library(fGarch)
fit = garchFit(~garch(1,1), oil)

y = fit@sigma.t
plot(window(oil, start=900, end=1000))
lines(window(oil-2*y, start=900, end=1000), col=4)
lines(window(oil+2*y, start=900, end=1000), col=4)


par(mfrow=c(3,1))
plot.ts(cmort)
plot.ts(tempr)
plot.ts(part)

dev.new()
pairs(cbind(cmort, tempr, part))
# cmort and temp are quadratically related
# cmort and part are linearly related
plot(time(cmort), cmort)
# cmort and time are linearly related

temp = tempr - mean(tempr)
temp2 = temp^2
regfit = lm(cmort ~ time(cmort) + temp + temp2 + part)
acf2(regfit$residuals)
# residuals are ARMA(2,0,0)

arimafit = arima(cmort, order=c(2,0,0), xreg=cbind(time(cmort), temp, temp2, part))
acf2(arimafit$residuals)


library(vars)
x = cbind(cmort, tempr, part)
plot.ts(x)
acf(x)

xnew = diff(x)
acf(xnew)

fit <- VAR(xnew, p=1)
summary(fit)
acf(resid(fit))
serial.test(fit, lags.pt=20)

VARselect(xnew, lag.max=10)

fit <- VAR(xnew, p=3)
summary(fit)
acf(resid(fit))
serial.test(fit, lags.pt=20)

fit.predict = predict(fit, n.ahead=12, ci=0.95)
fanchart(fit.predict)