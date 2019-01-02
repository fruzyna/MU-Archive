# April 12 In Class Notes
library(astsa)

# Setup for the model
y = cbind(gtemp, gtemp2)
n = nrow(y)
input = rep(1, n)
A = array(rep(1,2), dim=c(2,1,n))
mu0 = 0.26; sigma0 = 0.01; phi = 1

# Likelyhood Function
Likfn = function(para)
{
  cQ = para[1]
  cR11 = para[2]
  cR22 = para[3]
  cR12 = para[4]
  cR = matrix(c(cR11, cR12, 0, cR22), 2) # put 3 numbers in 2x2 matrix
  drift = para[5]
  kf = Kfilter1(n, y, A, mu0, sigma0, phi, drift, 0, cQ, cR, input) # run the values through the K filter
  return(kf$like)
}

init.par = c(0.1, 0.1, 0.1, 0, 0.05) # initial params

# plug params into function
est = optim(init.par, Likfn, NULL, method='BFGS', hessian=TRUE) # estimates
SE = sqrt(diag(solve(est$hessian))) # standard errors

# get and format outputs
estoutput = cbind(estimates=est$par, SE)
rownames(estoutput) = c('cQ', 'cR11', 'cR22', 'cR12', 'drift')
estoutput

# Perform t-test on drift
drift_pos = 5
est$par[drift_pos] / SE[drift_pos] # < 1.96, cannot reject H_0

# Get parameters
cQ = est$par[1]
cR11 = est$par[2]
cR22 = est$par[3]
cR12 = est$par[4]
drift = est$par[5]

# Put into matrix
cR = matrix(c(cR11, 0, cR12, cR22), 2)
R = t(cR) %*% cR # multiple the matrix cR by its transpose

# Smooth
ks = Ksmooth1(n, y, A, mu0, sigma0, phi, drift, 0, cQ, cR, input)

# Plot
xsmooth = ts(as.vector(ks$xs), start=1880)
plot.ts(xsmooth, lty=1)
lines(gtemp, col='blue', lty=2)
lines(gtemp2, col='red', lty=2)