% Liam Fruzyna
% COSC 3410
% Assignment 6
% Option 2

crimes([charge(norma,_),charge(andy,_),charge(edward,_),charge(olivia,_)]).

solution(List) :- crimes(List),
	member(charge(_,stopsign),List),
	member(charge(_,speeding),List),
	member(charge(_,leftturn),List),
	member(charge(_,redlight),List),

	member(charge(norma,speeding),List); member(charge(norma,leftturn),List),
	member(charge(andy,stopsign),List); member(charge(andy,leftturn),List); member(charge(andy, redlight),List),
	member(charge(edward,leftturn),List),
	member(charge(olivia,speeding),List); member(charge(olivia,leftturn),List); member(charge(olivia, redlight), List).
