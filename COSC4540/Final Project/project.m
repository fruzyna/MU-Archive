% Program 6.6 Animation program for bridge using IVP solver
% Inputs: inter = [a b] time interval, 
%   ic = [y(1,1) y(1,2) y(1,3) y(1,4)],
%   h = stepsize, p = steps per point plotted
% Calls a one-step method such as trapstep.m
% Example usage: tacoma([0 1000],[0 0 0.001 0],.04,3);

inter = [0 1000];
ic = [0 0 0.001 0];
h = 0.04;
p = 3;
tacoma(inter, ic, h, p, "euler", true)

%ic = [0 0 0.0001 0];
%tacoma(inter, ic, h, p, "rk", false)

%ic = [0 0 0.00001 0];
%tacoma(inter, ic, h, p, "rk", false)

%ic = [0 0 0.000001 0];
%tacoma(inter, ic, h, p, "rk", false)


function magFac=tacoma(inter,ic,h,p,method,draw)
    clf                                  % clear figure window
    a = inter(1);
    b = inter(2);
    n = ceil((b-a)/(h*p));                 % plot n points
    y(1,:) = ic;                           % enter initial conds in y
    t(1) = a;
    len = 6;
    
    % setup for plot
    if draw
        set(gca,'XLim',[-8 8],'YLim',[-8 8], ...
           'XTick',[-8 0 8],'YTick',[-8 0 8], ...
           'Drawmode','fast','Visible','on','NextPlot','add');
        cla;                                 % clear screen
        axis square                          % make aspect ratio 1 - 1
        road = line('color','b','LineStyle','-','LineWidth',5,...
            'erase','xor','xdata',[],'ydata',[]);
        lcable = line('color','r','LineStyle','-','LineWidth',1,...
            'erase','xor','xdata',[],'ydata',[]);
        rcable = line('color','r','LineStyle','-','LineWidth',1,...
            'erase','xor','xdata',[],'ydata',[]);
    end
    
    for k=1:n
        % calculate p iterations for every frame drawn
        for i=1:p
            t(i+1) = t(i)+h;
            ycurr = y(i,:);
            
            % calculate the new y value using the requested method
            switch(method)
                case "trap"
                    ycurr = trapstep(t(i),ycurr,h);
                case "euler"
                    ycurr = eulerstep(t(i),ycurr,h);
                case "rk"
                    ycurr = rkstep(t(i),ycurr,h);
            end
            y(i+1,:) = ycurr;
        end
        % make list of ys and thetas
        height(k*p+1:k*p+p+1) = y(:,1);
        theta(k*p+1:k*p+p+1) = y(:,3);
        
        % calculate position of bridge
        y(1,:) = y(p+1,:);
        t(1) = t(p+1);
        c = len*cos(y(1,3));
        s = len*sin(y(1,3));
        
        if draw
            % draw bridge
            set(road,'xdata',[-c c],'ydata',[-s-y(1,1) s-y(1,1)])
            set(lcable,'xdata',[-c -c],'ydata',[-s-y(1,1) 8])
            set(rcable,'xdata',[c c],'ydata',[s-y(1,1) 8])
            drawnow;
            %pause(h)
        end
    end
    % calculate the magnification factor
    magFac = max(abs(theta))/ic(1,3);
    if draw
        figure
        plot(height)
        figure
        plot(theta)
    end
end

function y=trapstep(t,x,h)
    %one step of the Trapezoid Method
    z1 = ydot(t,x); % z1 and x are 4-tuples
    g = x+h*z1;
    z2 = ydot(t+h,g);
    y = x+h*(z1+z2)/2;
end

function y=eulerstep(t,x,h)
    %one step of Euler's Method
    y = x+h*ydot(t,x);
end

function y=rkstep(t,x,h)
    %one step of the Runge-Kutta Method
    s1 = ydot(t,x);
    s2 = ydot(t+h/2,x+h*s1/2);
    s3 = ydot(t+h/2,x+h*s2/2);
    s4 = ydot(t+h,x+h*s3);
    y = x+h*(s1+2*s2+2*s3+s4)/6;
end

function yd=ydot(t,y)
    len = 6;
    a = 0.2;
    W = 80;
    d = 0.01;
    
    omega = 2*pi*38/60;
    a1 = exp(a*(y(1)-len*sin(y(3))));
    a2 = exp(a*(y(1)+len*sin(y(3))));
    
    yd(1) = y(2);
    yd(2) = -d*y(2) - 0.4/a * (a1+a2-2) + 0.2*W*sin(omega*t);
    yd(3) = y(4);
    yd(4) = -d*y(4) + 1.2*cos(y(3))/(len*a) * (a1-a2);
end