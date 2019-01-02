# Liam Fruzyna
# MATH 4760
# Final Exam Part 1

library(astsa)

# 1)
plot.ts(qinfl)
acf2(qinfl) # Needs to be differenced
dinfl = diff(qinfl)
acf2(dinfl)

sarima(qinfl, 1, 1, 3)
sarima.for(qinfl, 12, 1, 1, 3)

sarima(qinfl, 2, 1, 2, P=0, D=0, Q=2, S=4)
sarima.for(qinfl, 12, 2, 1, 2, P=0, D=0, Q=2, S=4)

# 2)
qinfl[1]
var(qinfl[1:4])

n=length(qinfl)
mu0=c(1.67, 0, 0, 0); Sigma0=diag(2.31, 4);
A=t(c(1, 1, 0, 0))
#Likelihood Function
Likfun=function(par){
  Phi = diag(0, 4); Phi[1,1]=par[1];
  Phi[2, ]=c(0, -1, -1, -1); Phi[3, ]=c(0, 1, 0, 0); Phi[4, ]=c(0, 0, 1, 0);
  cQ=diag(0, 4)
  cQ[1, 1]=par[2]; cQ[2,2] = par[3];
  cR=par[4]
  Kf=Kfilter0(n, qinfl, A, mu0, Sigma0, Phi, cQ, cR)
  return(Kf$like) }
init.par = c(1.05, 0.1, 0.1, 0.5)
est = optim(init.par, Likfun, NULL, method="BFGS", hessian=TRUE, control=list(trace=1, REPORT=1))
SE=sqrt(diag(solve(est$hessian)))
u = cbind(estimate=est$par, SE)
rownames(u)=c("Phi", "sigz1", "sigz2", "sigv")

# a)
#Smoothing
Phi = diag(0, 4); Phi[1,1]=est$par[1];
Phi[2, ]=c(0, -1, -1, -1); Phi[3, ]=c(0, 1, 0, 0); Phi[4, ]=c(0, 0, 1, 0);
cQ=diag(1, 4)
cQ[1,1]=est$par[2]; cQ[2,2]=est$par[3]; cR=est$par[4]
Phi; cQ;
ks = Ksmooth0(n, qinfl, A, mu0, Sigma0, Phi, cQ, cR)
Ts=ts(ks$xs[1,,], start=1953, freq=4)
Ss=ts(ks$xs[2,,], start=1953, freq=4)
p1=ts(2*sqrt(ks$Ps[1,1,]), start=1953, freq=4)
p2=ts(2*sqrt(ks$Ps[2,2,]), start=1953, freq=4)
par(mfrow=c(3,1))
plot(Ts, main="Trend")
lines(Ts+p1, lty=2, col="red"); lines(Ts-p1, lty=2, col="red")
plot(Ss, main="Seasonal")
lines(Ss+p2, lty=2, col="red"); lines(Ss-p2, lty=2, col="red")
plot(qinfl, type="p", main="Smooth Series")
lines(Ts+Ss)
lines(Ts+Ss+p1+p2, lty=2, col="red"); lines(Ts+Ss-p1-p2, lty=2, col="red")

# b)
# Forecasting
n.ahead=12
y=ts(append(qinfl, rep(0, n.ahead)), start=1953, freq=4)
rmspe=rep(0, n.ahead); x00=ks$xf[ , , n]; P00=ks$Pf[ , , n]
Q=t(cQ)%*%cQ; R=t(cR)%*%cR;
for (i in 1:n.ahead) {
  xp=Phi%*%x00; Pp=Phi%*%P00%*%t(Phi) + Q;
  sigsqy=A%*%Pp%*%t(A) + R; K= Pp%*%t(A)%*%(1/sigsqy)
  x00 = xp; P00 = Pp-K%*%A%*%Pp
  y[n+i] = A%*%xp; rmspe[i]=sqrt(sigsqy) }
plot(y, type="o", xlim=c(1953, 1984))
upperlim = ts(y[(n+1): (n+n.ahead)] + 2*rmspe, start=1980.75, freq=4)
lowerlim = ts(y[(n+1): (n+n.ahead)] - 2*rmspe, start=1980.75, freq=4)
lines(upperlim, lty=2, col="red")
lines(lowerlim, lty=2, col="red")
abline(v=1980.75, lty=3)