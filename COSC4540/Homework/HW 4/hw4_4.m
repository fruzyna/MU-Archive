% Liam Fruzyna
% COSC 4540
% Homework 4 #4

clear all; close all

gauss(100);
gauss(1000);

function[x] = gauss(n)
    N = 200; % number of iterations

    %%% Create A matrix in sparse format
    %a = zeros(n,n);
    e = ones(n,1);
    n2 = n/2;
    a = spdiags([-e 3*e -e], -1:1,n,n);
    A = a;
    %%% Create b vector
    b = zeros(n,1);
    b(1) = 2;
    b(n) = 2;
    b(2:n-1) = 1;

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
    DplusL = D + L;
    DLinv = DplusL^(-1);
    
    tol = 10^(-6);
    iterations = 0;

    %Gauss-Seidel iteration
    while abs(max(abs(x)) - xexact) > tol
      x = DLinv*(b - U*x);
      iterations = iterations + 1;
    end

    format long
    format compact
    
    n
    maxX = max(x)
    maxError = max(abs(A*x - b))
    iterations
end