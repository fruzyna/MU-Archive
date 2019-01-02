%Jacobi to Solve Ax=b

clear all; close all
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
N = 200; % number of iterations

%Enter A matrix and b vector here.

n = 10; %dimension of the A matrix
%%% Create A matrix in sparse format
%a = zeros(n,n);
e = ones(n,1);
n2 = n/2;
a = spdiags([e 2*e e], -1:1,n,n);
A = a;
%%% Create b vector
b = zeros(n,1);
b(1) = 1;
b(n) = -1;
b(2:n-1) = 0;

%%% Enter the exact soln to Ax = b
xexact(1:n) = 1;
xexact = xexact';

%%% Set up the L, U, D matrices
% Initialize the arrays
x = zeros(n,1);
D = zeros(n,n);
Dinv = zeros(n,n);
L = zeros(n,n);
U = zeros(n,n);

%Determine D
for i = 1:n                   
    D(i,i) = A(i,i);
end
Dinv = D^(-1);

%Determine L, U and L+U
for i = 1:n
    for j = i:n
        U(i,j) = A(i,j);
    end
end
U = U - D;
L = A - U - D;
LplusU = L + U;

%Jacobi iteration
for k = 1:N
  x = Dinv*(b - LplusU*x); %Jacobi
end

format long
format compact
x
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%





    
