# January 25 In Class Notes
require(astsa)

# r basics
# create array with column
x = c(1,2,5,6)
x[4] # 6

# can do math on whole array
y = 5 * x
y
z = x * y
z

# make a big empty array
k = c(rep(0, 1000)) # repeat 0 1000 times
k

# astsa book data
# jj, Johnson & Johnson Quarterly Earnings
jj
plot(jj, type='o', ylab='Quarterly Earnings 1960 Q1+')

# global temperature
plot(gtemp, type='o', ylab='Global Temperature')

#random numbers
w = rnorm(500, 0, 1) # 500 numbers from 0 to 1
mavg = filter(w, sides=2, c(rep(1/3, 3))) # moving average
par(mfrow=c(2,1)) # plot for 2 plots in 2 rows
plot.ts(w, main='White Noise') # ts for time series
plot.ts(x, main='Moving Average')