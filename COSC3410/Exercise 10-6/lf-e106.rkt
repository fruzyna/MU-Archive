#lang plai

(define-type F1WAE
  [num (n number?)]
  [add (lhs F1WAE?) (rhs F1WAE?)]
  [with (name symbol?) (named-expr F1WAE?) (body F1WAE?)]
  [id (name symbol?)]
  [if0 (val F1WAE?) (suc F1WAE?) (fail F1WAE?)]
  [app (fun-name symbol?) (arg F1WAE?)])

(define-type FunDef
  [fundef (fun-name symbol?)
          (arg-name symbol?)
          (body F1WAE?)])

;; lookup-fundef: symbol listof(FunDef) --> FunDef
(define (lookup-fundef name defs)
  (cond [(null? defs) (error name "definition not found")]
        [(symbol=? name (fundef-fun-name (car defs))) ;; using the accessor function for fun-name
             (car defs)]
        [else (lookup-fundef name (cdr defs))]
     ))

(define (subst expr sub-id val)
  (type-case F1WAE expr
    [num (n) expr]
    [add (l r) (add (subst l sub-id val)
                    (subst r sub-id val))]
    [if0 (v s f) (if0 (subst v sub-id val)
                      (subst s sub-id val)
                      (subst f sub-id val))]
    [with (bound-id named-expr bound-body)
          (if (symbol=? bound-id sub-id)
              (with bound-id
                    (subst named-expr sub-id val)
                    bound-body)
              (with bound-id
                    (subst named-expr sub-id val)
                    (subst bound-body sub-id val)))]
    [app (fun-name arg-expr)
         (app fun-name (subst arg-expr sub-id val))]
    [id (v) (if (symbol=? v sub-id) val expr)]))

;; interp: F1WAE listof(FunDef) --> number
(define (interp expr fun-defs)
  (type-case F1WAE expr
    [num (n) n]
    [add (l r) (+ (interp l fun-defs) (interp r fun-defs))]
    [if0 (v s f) (cond [(eq? (interp v fun-defs) (0)) (interp s fun-defs)]
                       [else (interp f fun-defs)])]
    [with (bound-id named-expr bound-body)
          (interp (subst bound-body
                         bound-id
                         (num (interp named-expr fun-defs)))
                  fun-defs)]
    [id (v) (error 'interp "free identifier: ~a" v)]
    [app (fun-name arg-expr)
         (let ([the-fun-def (lookup-fundef fun-name fun-defs)])
           (interp (subst (fundef-body the-fun-def)
                          (fundef-arg-name the-fun-def)
                          (num (interp arg-expr fun-defs)))
                   fun-defs))]
  ))

;; parse: s-expr -->F1WAE
(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(eq? (first sexp) '+) (add (parse (second sexp))
                                    (parse (third sexp)))]
        [(eq? (first sexp) 'if0) (if0 (parse (second sexp))
                                    (parse (third sexp))
                                    (parse (fourth sexp)))]
        [(eq? (first sexp) 'with) (with (first (second sexp))
                                    (parse (second (second sexp)))
                                    (parse (third sexp)))]
        [(symbol? (first sexp)) (app (first sexp) 
                                     (parse (second sexp)))]
        [ else (error 'parse "unexpected operator: ~a" (first sexp))]
     ))

;; sample program for Krishnamurthi p.29
;; NOTE: we pass the tree created by parse AND a list of function definitions
(interp (parse '{double {double 5}})
        (list (fundef 'double
                      'n
                      (add (id 'n) (id 'n)))))

;; list of function defs just play around with
(define dlist (list (fundef 'add2 'n
                            (add (id 'n) (num 2)))
                    (fundef 'five 'dummy
                            (num 5))
                    (fundef 'add4 'm
                            (app 'add2 (app 'add2 (id 'm))))
                    (fundef 'addn 'n 'val
                            (if0 (id 'n) ; if n = 0
                                 (num 0) ; true, return 0
                                 (add (id 'val)
                                      (app 'addn (add (id 'n) (num -1))
                                                 (id 'val))))) ; false, return val + add n-1 val
                    ))

;; our language doesn't include function definition (we have
;; to do that by hand in a separate list), but we can make
;; the separate list look a little more friendly by parsing
;; the function bodies:
;(define dlist (list (fundef 'add2 'n
;                            (parse '{+ n 2}))
;                    (fundef 'five 'dummy
;                            (parse '5))
;                    (fundef 'add4 'm
;                            (parse '{add2 {add2 m}}))
;                    ))
