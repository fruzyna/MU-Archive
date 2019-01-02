# April 17 In Class Notes
library(astsa)

plot.ts(jj)
n=length(jj)

mu0 = c(0.5, 0, 0, 0)
sigma0 = diag(0.1, 4)
A = t(c(1, 1, 0, 0)) # transform the vector

# Likelyhood function
Likefun = function(par)
{
  phi = diag(0,4) # make a matrix
  phi[1,1] = par[1]
  phi[2,] = c(0, -1, -1, -1) # fill the matrix row
  phi[3,] = c(0, 1, 0, 0)
  phi[4,] = c(0, 0, 1, 0)
  cQ = diag(0,4)
  cQ[1,1] = par[2]
  cQ[2,2] = par[3]
  cR = par[4]
  Kf = Kfilter0(n, jj, A, mu0, sigma0, phi, cQ, cR)
  return(Kf$like)
}

init.par = c(1.05, 0.1, 0.1, 0.5)

est = optim(init.par, Likefun, NULL, method='BFGS', hessian=TRUE, control=list(trace=1, REPORT=1))
SE = sqrt(diag(solve(est$hessian)))
u = cbind(estimate=est$par, SE)
rownames(u) = c('Phi', 'sigz1', 'sigz2', 'sigv')
u

# Smoothing
phi = diag(0, 4)
phi[1,1]=est$par[1]
phi[2, ]=c(0, -1, -1, -1)
phi[3, ]=c(0, 1, 0, 0)
phi[4, ]=c(0, 0, 1, 0);
cQ=diag(1, 4)
cQ[1,1]=est$par[2]
cQ[2,2]=est$par[3]
cR=est$par[4]
phi; cQ;

ks = Ksmooth0(n, jj, A, mu0, sigma0, phi, cQ, cR)

Ts=ts(ks$xs[1,,], start=1960, freq=4)
Ss=ts(ks$xs[2,,], start=1960, freq=4)
p1=ts(2*sqrt(ks$Ps[1,1,]), start=1960, freq=4)
p2=ts(2*sqrt(ks$Ps[2,2,]), start=1960, freq=4)

par(mfrow=c(3,1))
plot(Ts, main="Trend")
lines(Ts+p1, lty=2, col="red")
lines(Ts-p1, lty=2, col="red")

plot(Ss, main="Seasonal")
lines(Ss+p2, lty=2, col="red")
lines(Ss-p2, lty=2, col="red")

plot(jj, type="p", main="Smooth Series")
lines(Ts+Ss)
lines(Ts+Ss+p1+p2, lty=2, col="red")
lines(Ts+Ss-p1-p2, lty=2, col="red")

# Forecasting
n.ahead=12
y=ts(append(jj, rep(0, n.ahead)), start=1960, freq=4)
rmspe=rep(0, n.ahead)
x00=ks$xf[ , , n]
P00=ks$Pf[ , , n]
Q=t(cQ)%*%cQ
R=t(cR)%*%cR

for (i in 1:n.ahead)
{
  xp=phi%*%x00; Pp=phi%*%P00%*%t(phi) + Q;
  sigsqy=A%*%Pp%*%t(A) + R; K= Pp%*%t(A)%*%(1/sigsqy)
  x00 = xp; P00 = Pp-K%*%A%*%Pp
  y[n+i] = A%*%xp; rmspe[i]=sqrt(sigsqy)
}

plot(y, type="o", xlim=c(1975, 1984))
upperlim = ts(y[(n+1): (n+n.ahead)] + 2*rmspe, start=1981, freq=4)
lowerlim = ts(y[(n+1): (n+n.ahead)] - 2*rmspe, start=1981, freq=4)
lines(upperlim, lty=2, col="red")
lines(lowerlim, lty=2, col="red")
abline(v=1980.75, lty=3)