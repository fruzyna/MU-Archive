% Harbor queueing simulation
clear all; close all

%%DEFINE ALL VARIABLES
%between(i) = time between arrival of ship i and i-1.
%arrive(i) = time at which ship i arrives to harbor
%unload(i) = time to unload ship i
%start(i) = time at which ship i begins getting unloaded
%idle(i) = time harbor is idle for ship i
%wait(i) = time ship i waits in line
%finish(i) time ship i leaves harbor
%harbor(i) = total time at harbor for ship i

%CUMULATIVE VARIABLES
%hartime = average time per ship in the harbor
%maxtime = max time in the harbor
%waittime = avg wait time per ship
%maxwait = max waiting time
%idletime = percent of total sim time harbor is idle

%% ENTER input variables
N = 1000; % number of ships to simulate


% #2 Parameters
aunload = 10;
bunload = 20;
aarrive = 20;
barrive = 30;

% #3 Parameters
%aunload = 50;
%bunload = 70;
%aarrive = 49;
%barrive = 70;


maxQ = 25;

 % Interarrival times are uniformly distributed.
 % Service times are constant.

%% INITIALIZE variables

%ship 1 is special case
between(1) = 0;
arrive(1) = 0 + between(1);
unload(1) = rand * (bunload - aunload) + aunload;
start(1) = arrive(1);
wait(1) = 0;
finish(1) = unload(1);
harbor(1) = unload(1);
idle(1) = 0;
qlength(1) = 0;

% cumulative variables
left = 0;


%% RUN the simulation for N ships

for i = 2:N
    %determine interarrival and unload times
    between(i) = rand * (barrive - aarrive) + aarrive;
    unload(i) = rand * (bunload - aunload) + aunload;
    arrive(i) = arrive(i-1) + between(i);
    %determine if dock is idle or not
    timediff =  arrive(i) - finish(i-1);
    if qlength(i-1) > maxQ
        left = left + 1;
        wait(i) = 0;
        idle(i) = 0;
        qlength(i) = qlength(i - 1);
    else
        if timediff > 0 %dock is idle %arrive(i)>finish(i-1)
            wait(i) = 0;
            idle(i) = timediff;
            qlength(i) = 0;
        else
            wait(i) = -timediff;
            idle(i) = 0;
            if arrive(i) > start(i-1)
                qlength(i) = 1;
            else
                qlength(i) = qlength(i-1) + 1;
            end
        end
    end
    
    %calculate start and finish times, and duration in harbor
    start(i) = arrive(i) + wait(i);
    finish(i) = start(i) + unload(i);
    harbor(i) = finish(i) - arrive(i);
    
    %Update Cumulative Variables
   
    
    
end

%%%%%%%%%OUTPUT%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

avghartime = mean(harbor);
maxhar = max(harbor);
avgwaittime = mean(wait);
avgidletime = mean(idle);
maxwait = max(wait);
finaltime = finish(end);
fractimeidle = sum(idle)/finaltime

avgq = mean(qlength)
maxq = max(qlength)

%% Create a nice table
fprintf('Begin One Bay Simulation for %i ships\n', N)
fprintf('Avg time at harbor   Max time at harbor    Avg wait time   Max wait time  Percent Time harbor idle\n============================================================================================\n')
fprintf ('   %5.1f                 %5.1f               %5.1f             %5.1f              %5.1f\n',avghartime,maxhar,avgwaittime,maxwait,avgidletime)
 
figure(1);hist(between); title('Interarrival Times')
figure(2); hist(unload); title('Service Times');
figure(3); hist(harbor); title('Harbor Times');
figure(4); hist(wait); title('Wait Times');
figure(5); hist(idle); title('Idle Times');
figure(6); plot(qlength); title('Queue Lengths');
