#lang plai
; Liam Fruzyna
; COSC 3410
; Assignment 3

; atom? for Little Schemer
;; atom? : sexp -> boolean
(define atom?
  (lambda (x)
    (and (not (pair? x)) (not (null? x)))
))

;; Abstract trees for "with" with arithmetic expressions (CWAE)
(define-type CWAE
  [num (n number?)]
  [add (lhs CWAE?) (rhs CWAE?)]
  [sub (lhs CWAE?) (rhs CWAE?)]
  [mul (lhs CWAE?) (rhs CWAE?)]
  [div (lhs CWAE?) (rhs CWAE?)]
  [neg (val CWAE?)]
  [with (name symbol?) (named-expr CWAE?) (body CWAE?)]
  [if< (exp1 CWAE?) (exp2 CWAE?) (val1 CWAE?) (val2 CWAE?)]
  [id (name symbol?)]
)

;; parse : sexp -> CWAE
;; to convert s-expressions into CWAEs
(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(list? sexp)
         (case (first sexp)
           [(+) (cond [(= (length sexp) 2) (add (parse (second sexp)) (parse (third sexp)))]
                      [else (error 'parse "(+) uses 2 arguments")])]
           [(-) (cond [(= (length sexp) 1) (neg (parse (second sexp)))]
                      [(= (length sexp) 2) (sub (parse (second sexp)) (parse (third sexp)))]
                      [else (error 'parse "(-) uses 1 or 2 arguments")])]
           [(*) (cond [(= (length sexp) 2) (mul (parse (second sexp)) (parse (third sexp)))]
                      [else (error 'parse "(*) uses 2 arguments")])]
           [(/) (cond [(= (length sexp) 2) (div (parse (second sexp)) (parse (third sexp)))]
                      [else (error 'parse "(/) uses 2 arguments")])]
           [(with) (cond [(= (length sexp) 2) (cond [(atom? (second sexp)) (error 'parse "the first argument in (with) uses 2 sub-arguments")]
                                                    [(eq? (car (second sexp)) 'quote) (error 'parse "the symbol in (with) should not be quoted")]
                                                    [else (with (first (second sexp))
                                                                (parse (second (second sexp)))
                                                                (parse (third sexp)))])]
                         [else (error 'parse "(with) uses 2 arguments")])]
           [(if<) (cond [(= (length sexp) 4) (if< (parse (second sexp))
                                                (parse (third sexp))
                                                (parse (fourth sexp))
                                                (parse (fifth sexp)))]
                      [else (error 'parse "(/) uses 4 arguments")])]
           [else (error 'parse "unknown operator")]
           )]
   ))

;; subst : CWAE symbol CWAE --> CWAE
;; substitutes second argument with third argument in first argument,
;; as per the rules of substitution; the resulting expression contains
;; no free instances of the second argument
(define(subst expr sub-id val)
    (type-case CWAE expr
        [num (n) expr]
        [add (l r) (add (subst l sub-id val) (subst r sub-id val))]
        [sub (l r) (sub (subst l sub-id val) (subst r sub-id val))]
        [mul (l r) (mul (subst l sub-id val) (subst r sub-id val))]
        [div (l r) (div (subst l sub-id val) (subst r sub-id val))]
        [neg (v) (neg (subst v sub-id val))]
        [if< (s1 s2 v1 v2) (if< (subst s1 sub-id val) (subst s2 sub-id val)
                                (subst v1 sub-id val) (subst v2 sub-id val))]
        [with (bound-id named-expr bound-body)
              (if (symbol=? bound-id sub-id)
                 (with bound-id (subst named-expr sub-id val) bound-body)
                 (with bound-id (subst named-expr sub-id val) (subst bound-body sub-id val)))]
        [id (v) (if (symbol=? v sub-id) val expr)]
   ))

; length : List --> number
;; counts the amount of arguments in a CWAE command
(define length
  (lambda (s)
    (cond [(null? (cdr s)) 0]
          [else (+ 1 (length (cdr s)))]
          )))

; calc : CWAE --> number
;; evaluates CWAE expressions by reducing them to numbers
(define (calc expr)
  (type-case CWAE expr
     [num (n) n]
     [add (l r) (+ (calc l) (calc r))]
     [sub (l r) (- (calc l) (calc r))]
     [mul (l r) (* (calc l) (calc r))]
     [div (l r) (/ (calc l) (calc r))]
     [neg (v) (* (calc v) -1)]
     [with (bound-id named-expr bound-body)
           (calc (subst bound-body bound-id (num (calc named-expr))))]
     [if< (s1 s2 v1 v2) (cond [(< (calc s1) (calc s2)) (calc v1)]
                              [else (calc v2)])]
     [id (v) (error 'calc "free identifier")]
  ))

; these should pass
(test (calc (parse '{+ 5 5})) 10)
(test (calc (parse '{- 5 5})) 0)
(test (calc (parse '{* 5 5})) 25)
(test (calc (parse '{/ 5 5})) 1)
(test (calc (parse '{- 5})) -5)
(test (calc (parse '{if< 5 4 1 0})) 0)
(test (calc (parse '{if< 4 5 1 0})) 1)
(test (calc (parse '{with (x 5) (+ x x)})) 10)
(test (calc (parse '{with {num1 -33}
                          {with {num2 876}
                                {with {num3 305}
                                      {if< num1 num2 {if< num2 num3 num3 num2}
                                           {if< num1 num3 num3 num1} }
                                      }}})) 876)
(test (calc (parse '{with {a 1}
                          {with {b 3}
                                {with {c 2}
                                      {/ {+ {- b} {- {* b b} {* {* 4 a} c}}} {* 2 a} }
                                      }}})) -1)
(test (calc (parse '{with {y 1}
                          {with {x 5}
                                {if< {- {/ x y}} {/ x y} 1 0}}})) 1)

; these should fail with my errors
;(parse '{+ 4 5 6})
;(parse '{+ 2})
;(parse '{with 6})
;(parse '{with 'x 6})
;(parse '{3 4 5})
;(parse '{- 6 5 2})
;(parse '{+})
;(parse '{with {x 32} {+ x 4} {+ x 10}})