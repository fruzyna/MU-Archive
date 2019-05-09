% Image a 3D annulus
clear all
close all

N = 81;  %Number of points in each direction
dx = 0.1; %Grid dimension
llim = -(round(N-1)/2);
ulim = -llim;
x = [llim:1:ulim]*dx;
y = x;
z = x;

%%Create a digital person, consisting of a sphere imbedded in a sphere
person = zeros(N,N,N);

R1 = 1;
R2 = 2;
mu1 = 0;
mu2 = 1;

for i = 1:N
    for j = 1:N
        for k = 1:N
            val = x(i)*x(i) + y(j)*y(j) + z(k)*z(k);
            if val < R2
                if val < R1
                    person(i,j,k) = mu1;
                else
                    person(i,j,k) = mu2;
                end
            end
        end
    end
end

%% Create an image of the person
xrayimag = zeros(N,N);
for i = 1:N
    for k = 1:N
        xrayimag(i,k) = sum(person(i,:,k));
    end
end
figure(1)
image(xrayimag)

figure(2)
plot(x,xrayimag((N-1)/2,:))

% %%Radon example
% figure(2)
% iptsetpref('ImshowAxesVisible','on')
%         I = zeros(100,100);
%         I(25:75, 25:75) = 1;
%         theta = 0:180;
%         [R,xp] = radon(I,theta);
%         imshow(R,[],'Xdata',theta,'Ydata',xp,'InitialMagnification','fit')
%         xlabel('\theta (degrees)')
%         ylabel('x''')
%         colormap(gca,hot), colorbar
%         
%                
%          P = phantom(128);
%         R = radon(P,0:179);
%         I1 = iradon(R,0:179);
%         I2 = iradon(R,0:179,'linear','none');
%         figure(3) 
%         subplot(1,3,1), imshow(P), title('Original')
%         subplot(1,3,2), imshow(I1), title('Filtered backprojection')
% %         subplot(1,3,3), imshow(I2,[]), title('Unfiltered backprojection')
