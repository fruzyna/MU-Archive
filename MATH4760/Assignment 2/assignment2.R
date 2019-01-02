# Liam Fruzyna
# MATH 4760
# Assignment 2

# 1 Simulate 700 observations of AR(1) process, remove first 100 observations
# X_t = Q*X_(t-1) + Z_t for Q = -0.9, -0.1, 0.1, 0.9
z = rnorm(700, 0, 1)
xa = filter(z, filter=c(-0.9), method="recursive")[-(1:100)] # filter and remove first 100 observations
xb = filter(z, filter=c(-0.1), method="recursive")[-(1:100)]
xc = filter(z, filter=c(0.1), method="recursive")[-(1:100)]
xd = filter(z, filter=c(0.9), method="recursive")[-(1:100)]

# a) draw the time series plots and their autocorrelation functions
par(mfrow=c(3, 2))
plot.ts(z, main="Unfiltered")
plot.ts(z, main="Unfiltered")
plot.ts(xa, main="Autoregressive (-0.9)")
plot.ts(xd, main="Autoregressive (0.9)")
plot.ts(xb, main="Autoregressive (-0.1)")
plot.ts(xc, main="Autoregressive (0.1)")

# b) comment on the structures of the data and the autocorrelation function
# The further negative the autoregressive function the more noisey the data appears.
# Near zero the autoregressive function does near nothing.
# The further positve the autoregressive function the less noisey the data appears.


# 2 Simulate 500 observations of MA(1) process
# X_t = Q*Z_(t-1) + 0.4 Z_(t-2) + Z_t for Q = -0.9, -0.1, 0.1, 0.9
z = rnorm(500, 0, 1)
xa = filter(z, filter=c(1, -0.9, 0.4), method="convolution")
xb = filter(z, filter=c(1, -0.1, 0.4), method="convolution")
xc = filter(z, filter=c(1, 0.1, 0.4), method="convolution")
xd = filter(z, filter=c(1, 0.9, 0.4), method="convolution")

# a) draw the time series plots and their moving average functions
par(mfrow=c(3, 2))
plot.ts(z, main="Unfiltered")
plot.ts(z, main="Unfiltered")
plot.ts(xa, main="Moving Average (-0.9)")
plot.ts(xd, main="Moving Average (0.9)")
plot.ts(xb, main="Moving Average (-0.1)")
plot.ts(xc, main="Moving Average (0.1)")

# b) comment on the structures of the data and the moving average function
# The further negative the moving average function the more noisey the data appears.
# Near zero the moving average function does near nothing.
# The further positve the moving average function the less noisey the data appears.


# 3 Simulate the data of a signal plus noise model as desccribed in problem 1.2a (of the book)
s = c(rep(0,100), 10*exp(-(1:100)/20)*cos(2*pi*101:200/4))
x = ts(s + rnorm(200, 0, 1))

# b) draw the time series plot
par(mfrow=c(1,1))
plot.ts(x, main="Signal-Plus-Noise Model")
# and comment on the plot by comparing it with the time series plot in figure 1.7
# The data appears to be pure noise before t=100 because that is where the second function kicks in.
# It looks like the explosion without the warning at the beginning.
# The earthquake on the other hand appears most consistant after t=1000 because it doesn't fade out like out model.


# 4 Do all parts of problem 1.3
# a) generate 100 observations from the autoregression x_t = -0.9*x_(t-2) + w_t
z = rnorm(100, 0, 1)
x = filter(z, filter=c(0, 0.9), method="recursive")
# then apply the moving average filter v_t = (x_t + x_(t-1) + x_(t-2) + x_(t-3))/4
v = filter(x, filter=rep(1/4, 4), method="convolution")
# then plot
par(mfrow=c(3, 1))
plot.ts(z, main="Unfiltered")
plot.ts(x, main="Autoregressive")
plot.ts(v, main="Moving Average")

# b) repeat for x_t = cos(2*pi*t/4)
z = rnorm(100, 0, 1)
x = cos(2*pi*1:100/4)
v = filter(x, filter=rep(1/4, 4), method="convolution")
par(mfrow=c(3, 1))
plot.ts(z, main="Unfiltered")
plot.ts(x, main="Autoregressive")
plot.ts(v, main="Moving Average")

# c) repeat for x_t = cos(2*pi*t/4) + w_t
z = rnorm(100, 0, 1)
x = cos(2*pi*1:100/4) + z
v = filter(x, filter=rep(1/4, 4), method="convolution")
par(mfrow=c(3, 1))
plot.ts(z, main="Unfiltered")
plot.ts(x, main="Autoregressive")
plot.ts(v, main="Moving Average")

# d) compare and contrast all
# For part a the autoregressive function follows the trend of the unfiltered input but it is less noisey.
# The moving average on top of that removes a lot more noise making the graph look relatively smooth.
# Part b on the other hand is not affected by any noise so it is just a sharp sine wave.
# The moving average produces a weird effect where there is almost no noise but it increases toward the end.
# Part c being part b with added noise smooths out over time like in part a, but has a slight resemblance to a sine wave.