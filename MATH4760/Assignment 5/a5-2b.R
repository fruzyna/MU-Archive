# Liam Fruzyna
# MATH4760
# Assignment 5, 2b

library(astsa)

# The Model Setup
n= length(gtemp)
A=t(c(1, 0))
mu0=c(-0.26, -0.26);
Sigma0=diag(0.1, 2)
Phi=matrix(c(2, 1, -1, 0), 2)

#Likelihood Function
Likfn = function(para){
  cQ11=para[1]
  cR = para[2]
  cQ=matrix(c(cQ11,0, 0, 0), 2)
  kf = Kfilter0(n, gtemp, A, mu0, Sigma0, Phi, cQ, cR)
  return(kf$like) }

init.par = c(0.1, 0.1) #Initial parameters
est = optim(init.par, Likfn, NULL, method = "BFGS", hessian = TRUE, control=list(trace=1, REPORT=1))
SE = sqrt(diag(solve(est$hessian)))
estoutput = cbind(estimates=est$par, SE)
rownames(estoutput) = c("cQ11", "cR")
estoutput

cQ11=est$par[1]; cR=est$par[2]#Smoothing
cQ=matrix(c(cQ11, 0, 0, 0), 2)
Q = t(cQ)%*%cQ #matrix multiplication to get R matrix
ks = Ksmooth0(n, gtemp, A, mu0, Sigma0, Phi, cQ, cR)
xsmooth = ts(as.vector(ks$xs[1,,]), start=1880)
p=2*sqrt(ks$Ps[1,1,])
plot(xsmooth, lty=1)
lines(xsmooth-p, col="red", lty=2)
lines(xsmooth+p, col="red", lty=2)