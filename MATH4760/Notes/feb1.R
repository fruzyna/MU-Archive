# February 1 In Class Notes
require(astsa)

summary(fit <- lm(gtemp ~ time(gtemp))) # summarize the linear model of the data
plot.ts(gtemp, type='o', ylab='Global Temperature Deviations') # time series plot
abline(fit) # plot the linear model

# plot 3 data sets
par(mfrow = c(3,1))
plot(cmort, main='Cardiovascular Mortailiy')
plot(tempr, main='Temperature')
plot(part, main='Particulates')

# compare cmort to tempr
tfit <- lm(cmort ~ tempr)
plot(tempr, cmort)
abline(tfit)

# compare cmort to part
pfit <- lm(cmort ~ part)
plot(part, cmort)
abline(pfit)

# weird triple comparison
pairs(cbind(cmort, tempr, part))

trend = time(cmort)
ctemp = tempr - mean(tempr)
ctemp2 = ctemp^2
fit = lm(cmort ~ trend + ctemp + ctemp2 + part)
summary(fit)
summary(aov(fit))

plot.ts(gtemp)
acf(gtemp)
fit = lm(gtemp ~ time(gtemp))
par(mfrow=c(2,1))
plot.ts(resid(fit))
acf(resid(fit)) # autocorrelation functions