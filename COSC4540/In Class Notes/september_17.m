%Lagrange P2
%Interpolation

clear all
close all

xd = [1, 2, 4];
yd = log(xd);

plot(xd,yd,'or')
hold on
d1 = (xd(1) - xd(2))*(xd(1) - xd(3));
d2 = (xd(2) - xd(1))*(xd(2) - xd(3));
d3 = (xd(3) - xd(1))*(xd(3) - xd(2));

a = yd(1)/d1 + yd(2)/d2 +yd(3)/d3;
b = yd(1)*(-xd(2) - xd(3))/d1 + yd(2)*(-xd(1) - xd(3))/d2 +yd(3)*(-xd(1) - xd(2))/d3;
c = yd(1)*xd(2)*xd(3)/d1 + yd(2)*xd(1)*xd(3)/d2 +yd(3)*xd(1)*xd(2)/d3;


p= a.*xd.*xd + b.*xd + c;
x = 1:2:4;
papp = a.*x.*x + b.*x + c;

s = log(x);
plot(x,s)
plot(x,papp,'k')
figure(2)
plot(x,s - papp)
hold on
errorform = -(x - xd(1)).*(x - xd(2)).*(x - xd(3))/3;
plot(x,errorform,'k')