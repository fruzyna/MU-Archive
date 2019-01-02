% Liam Fruzyna
% COSC 3410
% Assignment 6
% Option 1

school :-
	courses(Courses),

	member(course(120, english, _, _), Courses),
	member(course(_, history, _, projector), Courses),
	member(course(200, _, smith, _), Courses),
	before(course(_, math, _, _), course(233, _, _, _), Courses),
	member(course(200, _, _, models), Courses),
	member(course(_, _, witherspoon, blackboard), Courses),
	before(course(_, _, johnson, _), course(_, science, _, _), Courses),
	Courses = [_, _, course(105, _, _, _), _],
	member(course(233, _, adams, _), Courses),
	Courses = [course(_, _, _, lecturn) | _],
	print_courses(Courses).

courses([course(_,_,_,_),
	course(_,_,_,_),
	course(_,_,_,_),
	course(_,_,_,_)]).

before(A, B, [A, B | _]).
before(A, B, [_ | Y]) :- before(A, B, Y).

print_courses([A|B]) :- !, write(A), nl, print_courses(B).
print_courses([]).

:- initialization(school).
