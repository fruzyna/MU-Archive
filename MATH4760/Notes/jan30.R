# January 30 In Class Notes
require(astsa)

# basic moving average
w = rnorm(500, 0, 1) # generates 500 random numbers within 1 std deviation of 0
x = filter(w, sides=2, (rep(1/3, 3))) # moving average
o = filter(w, sides=1, (rep(1/3, 3))) # 1 side
y = filter(w, sides=2, c(rep(1/10, 10)))
z = filter(w, sides=2, c(rep(1/20, 20)))
par(mfrow=c(4,1))
plot.ts(w, main="Unfiltered")
plot.ts(x, main="1/3 Moving Average")
plot.ts(o, main="1 Side 1/3 Moving Average")
plot.ts(y, main="1/10 Moving Average")
#plot.ts(z, main="1/20 Moving Average")

# simulating random walk
w = rnorm(1000, 0, 1)
s = cumsum(w) # adds up every previous w (s3 = w1 + w2 + w3 = s2 + w3)
par(mfrow=c(2,1))
plot.ts(w, main="Unfiltered")
plot.ts(s, main="Random Walk")

# simulating autoregressive process AR(1)
# X_t = 0.9 X_(t-1) + W_t
w = rnorm(550, 0, 1)
x = filter(w, filter=c(0.9), method="recursive")[-(1:50)] # filter and remove first 50 observations
par(mfrow=c(2,1))
plot.ts(w, main="Unfiltered")
plot.ts(x, main="Autoregressive")

# simulating moving average process MA(1)
# X_t = W_t + 0.9 W_(t-1)
w = rnorm(550, 0, 1)
x = filter(w, filter=c(1, 0.9), method="convolution")[-(1:50)]
par(mfrow=c(2,1))
plot.ts(w, main="Unfiltered")
plot.ts(x, main="Moving Average")
