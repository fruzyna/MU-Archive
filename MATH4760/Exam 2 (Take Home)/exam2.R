# Condensed Exam 2 #5

library(astsa)
library(FitAR)

# a) plot
# b) transform
# c) remove trend(s)
# d) plot acf and pacf
# e) arma model
# f) check parameters
# g) diagnostic (box-ljung, p-values)
# h) equation
# i) forecast


# 1 SO2
plot.ts(so2)
acf2(so2)

diff = diff(so2)
plot.ts(diff)
acf2(diff)

sarima(so2, 4, 1, 7)
sarima.for(so2, 20, 4, 1, 7)


# 2 OIL
plot.ts(oil)
acf2(oil)

log = log(oil)
difflog = diff(log)
plot.ts(difflog)
acf2(difflog)

sarima(oil, 1, 1, 3)
sarima.for(oil, 20, 1, 1, 3)


# 3 GTEMP
plot.ts(gtemp)
acf2(gtemp)

diff = diff(gtemp)
plot.ts(diff)
acf2(diff)

sarima(gtemp, 1, 1, 3)
sarima.for(gtemp, 20, 1, 1, 3)


# 4 JJ
plot.ts(jj)
acf2(jj)

log = log(jj)
difflog = diff(log)
plot.ts(difflog)
acf2(difflog)

sarima(jj, 5,1,1, 1,1,3, 4)
sarima.for(jj, 20, 5,1,1, 1,1,3, 4)

