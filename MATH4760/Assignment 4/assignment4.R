# Liam Fruzyna
# MATH 4760
# Assignment 4

library(astsa)
abs = abs(nyse)
acf2(nyse)

library(fracdiff)
library(fGarch)
library(vars)

# 1 Consider the absolute values of the nyse returns data
# a Check using ACF whether abs(nyse) follows long memory or short memory
acf2(abs)
# Long memory, doesn't cut off or exponentially decay
# b Fit ARFIMA with appropriate order. Make sure to test the residuals.
anyse = abs - mean(abs)
nyse.fd = fracdiff(anyse, nar=22, nma=6, M=30)
nyse.fd
fracdiff = diffseries(anyse, nyse.fd$d)
acf2(fracdiff)
Box.test(fracdiff, type='Ljung', lag=20)

# 2 Weekly crude oil spot prices in dollars per barrel are in oil
# a Investigate whether the growth rate of the weekly oil prices exhibit GARCH behavior
plot.ts(oil, ylab='Oil Prices')
acf2(oil)
# b Is the weekly growth rate white noise?
# No, not white noise.
# c Fit an appropriate GARCH model
fit = garchFit(~garch(1, 1), oil)

y = fit@sigma.t
plot(window(oil, start=2000, end=2010))
lines(window(oil-2*y, start=2000, end=2010), col=4)
lines(window(oil+2*y, start=2000, end=2010), col=4)


# 3 Let S_t represent the monthly sales data, sales, and L_t be a leading indicator lead
# a Plot the autocorrelation, acfs, or both sales and lead
acf(sales)
acf(lead)
# Also plot the cross-correlation ccf between sales and leads. Observer their behaviors.
ccf(sales, lead)
# b Compute the difference of sales \delta S_t and difference of lead \delta L_t.
delta_s = diff(sales)
delta_l = diff(lead)
# Repeat a for \delta S_t and \delta L_t.
acf(delta_s)
acf(delta_l)
ccf(delta_s, delta_l)
# c Fit the model \delta S_t = \Beta_0 + \Beta_1 \deltaL_{t-2} + \Beta_2 \delta L_{t-3} + x_t, where {x_t} is an ARMA process.
fit = arima(delta_s, order=c(3,0,0), xreg=delta_l)

# 4 Consider the data set econ5. Conceptrate only on the unemployment, gnp, and consumption.
# a Fit an appropriate vector ARMA (VAR) to x_t = (x_1t, x_2t, x_3t), where x_1t = log(U_t)- \Beta _0 - \Beta _1 t, x_2t = G_t, and x_3t = C_t. Meake sure to test for the residuals.
plot.ts(econ5)
acf(econ5)

x=cbind(econ5$unemp, econ5$gnp, econ5$consum)
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
# b Predict the unemployment of the next four quarters with 95% confidence intervals.
fit.predict = predict(fit, n.ahead=4, ci=0.95)
fanchart(fit.predict)