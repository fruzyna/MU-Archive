# March 20 In Class Notes

library(astsa)

plot.ts(prodn)
acf2(prodn) # nonstationary

# remove seasonal trend with diff
xprod = diff(prodn)
plot.ts(xprod)
acf2(xprod) # jump every 12 months, periodicty of order 12

# seasonal arima
sarima(prodn, 3,1,0, 1,0,0, 12)
# seems pretty good but there are some residuals
sarima(prodn, 3,1,0, 0,1,1, 12) # make some adjustments, apparently better

# forecast next 20 values
sarima.for(prodn, 20, 2,1,0, 0,1,1, 12)


#exam?
plot.ts(so2)
acf2(so2)

plot.ts(diff(so2))
acf2(diff(so2))
fit=arima(so2, order=c(0,1,1), method='ML', include.mean=TRUE)
acf2(fit$residual)
list(fit)

# lets try d=3
fit=arima(so2, order=c(0,3,1), method='ML', include.mean=TRUE)
acf2(fit$residual)
list(fit)
# want AIC to be smaller, so d=3 is bad, always check residuals

# see end of ch8 slides for more details
# sarima does most of this all in one, is okay on exam