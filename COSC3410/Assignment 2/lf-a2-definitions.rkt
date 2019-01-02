#lang racket
; Liam Fruzyna
; COSC 3410
; Assignment 2

; atom?: s-expression --> boolean
; returns whether or not a s-expression is an atom
(define atom?
  (lambda (x)
    (and (not (pair? x)) (not (null? x)))))

; differences: list --> list
; takes a list of numbers calculates the differences between them and returns a list of the differences
(define differences
  (lambda (s)
    (cond [(or (null? s) (null? (cdr s))) '()] ;return an empty list if the list has 1 or less items
          [else (cons (- (cadr s) (car s)) (differences (cdr s)))] ;otherwise subtract the first item from the second and put that in a new list before the next difference
)))

; zip: list list --> list
; takes 2 lists and combines the s-expressions in each position in the list
(define zip
  (lambda (s1 s2)
    (cond [(and (null? s1) (null? s2)) '()] ;if both lists are null returns an empty list
          [(null? s1) (error 'zip "first list too short")] ;throw an error if only the first list is empty
          [(null? s2) (error 'zip "second list too short")] ;throw an error if only the second list is empty
          [else (append (list (list (car s1) (car s2))) (zip (cdr s1) (cdr s2)))] ;combine the first of each list into their own list and move to the next item
)))

; deep-add: list --> integer
; adds every number in a given list including sublists
(define deep-add
  (lambda (s)
    (cond [(null? s) 0] ;return 0 if the list is empty
          [(number? (car s)) (+ (car s) (deep-add (cdr s)))] ;add the first item and the rest of the list if the first item is a number
          [(not (atom? (car s))) (+ (deep-add (car s)) (deep-add (cdr s)))] ;if the first item is a list add up the inside of the sublist and add that to the rest of the list
          [else (deep-add (cdr s))] ;if the item is a non-numeric atom skip it and add up the rest of the list
)))

; shape: list --> list
; removes all items from a list and its sublists so only a skeleton remains
(define shape
  (lambda (s)
    (cond [(null? s) '()] ;an empty list is good
          [(atom? (car s)) (shape (cdr s))] ;remove the first s-expression if it is an atom
          [else (cons (shape (car s)) (shape (cdr s)))] ;otherwise continue into the first sublist
)))