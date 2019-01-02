#lang racket
(define atom?
  (lambda (x)
    (and (not (pair? x)) (not (null? x))
)))

;Liam Fruzyna
;COSC 3410
;Assignment 1

;(dropcount numList) returns the amount of times in a numeric list the value goes down from one atom to the next
(define dropcount
  (lambda (numList)
    (cond [(or (null? numList) (null? (cdr numList))) 0] ;return 0 if the list is empty or only has 1 atom
          [(< (cadr numList) (car numList)) (+ 1 (dropcount (cdr numList)))] ;add one and recur if the next item drops down in value
          [else (dropcount (cdr numList))] ;otherwise recur
)))

;(beginning listA listB) returns the boolean of if listB starts with listA
(define beginning?
  (lambda (listA listB)
    (cond [(null? listA) #t] ;true if listA is null
          [(null? listB) #f] ;false if listB is null and listA is not
          [(equal? (car listA) (car listB)) (beginning? (cdr listA) (cdr listB))] ;recur if the cars of listA and listB are equal
          [else #f] ;false if they are not equal
)))

;(sublist listA listB) returns the boolean of if listA is contained in listB
(define sublist?
  (lambda (listA listB)
    (cond [(null? listA) #t] ;true if listA is null
          [(null? listB) #f] ;false if listB is null and listA is not
          [(beginning? listA listB) #t] ;true if listA is the beginning of listB
          [else (sublist? listA (cdr listB))] ;otherwise remove the first atom from listB and recur
)))