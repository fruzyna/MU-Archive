# April 3 In Class Notes

library(astsa)
blood = cbind(WBC, PLT, HCT) # combines 3 datasets
blood = replace(blood, blood == 0, NA) # replace 0 values with null values
plot(blood, type='o', pch=19, xlab='Day')

# Run a Kalman Filter
n = 50
z = rnorm(n+1, 0, 1)
v = rnorm(n, 0, 1)
mu = cumsum(z) # make the cumulative sum of z
y = mu[-1] + v
time = 1:n
mu0 = 0; sigma0 = 1; phi = 1; cQ = 1; cR = 1;
ks = Ksmooth0(n, y, 1, mu0, sigma0, phi, cQ, cR) # smooth w/ Kalman

# Plot the datas
lines(ks$xp)
lines(ks$xp - 1.96*sqrt(ks$Pp), lty='dashed', col='blue')
lines(ks$xp + 1.96*sqrt(ks$Pp), lty='dashed', col='blue')