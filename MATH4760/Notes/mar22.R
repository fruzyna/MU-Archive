# March 22 In Class Notes

x1 = 2*cos(2*pi*1:100*6/100) + 3*sin(2*pi*1:100*6/100)
x2 = 4*cos(2*pi*1:100*10/100) + 5*sin(2*pi*1:100*10/100)
x3 = 6*cos(2*pi*1:100*40/100) + 7*sin(2*pi*1:100*40/100)

x = x1 + x2 + x3

par(mfrow=c(2,2))
plot.ts(x1)
plot.ts(x2)
plot.ts(x3)
plot.ts(x)

par(mfrow=c(1,1))
P = abs(2*fft(x)/100)^2
Fr = 0:99/100
plot(Fr, P, type='o')

