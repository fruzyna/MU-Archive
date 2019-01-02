#lang plai

(define-type AE [num (n number?)]
  [add (lhs AE?) (rhs AE?)]
  [sub (lhs AE?) (rhs AE?)]
  [mlt (lhs AE?) (rhs AE?)]
  [div (lhs AE?) (rhs AE?)]
  [frac-div (lhs AE?) (rhs AE?)]
  [quo-div (lhs AE?) (rhs AE?)]
  )


;; parse : sexp -> AE
;; to convert s-expressions into AEs
(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(list? sexp)
         (case (first sexp)
           [(+) (add (parse (second sexp))
                     (parse (third sexp)))]
           [(-) (sub (parse (second sexp))
                     (parse (third sexp)))]
           [(*) (mlt (parse (second sexp))
                     (parse (third sexp)))]
           [(/) (div (parse (second sexp))
                     (parse (third sexp)))]
           [(%) (frac-div (parse (second sexp))
                     (parse (third sexp)))]
           [(/_) (quo-div (parse (second sexp))
                     (parse (third sexp)))]
           )]
   ))


;; calc : AE -> number
;; computes the value of an arithmetic expression
(define (calc an-ae)
  (type-case AE an-ae
    [num (n) n]
    [add (l r) (+ (calc l) (calc r))]
    [sub (l r) (- (calc l) (calc r))]
    [mlt (l r) (* (calc l) (calc r))]
    [div (l r) (* 1.0 (/ (calc l) (calc r)))]
    [frac-div (l r) (/ (calc l) (calc r))]
    [quo-div (l r) (quotient (calc l) (calc r))]
 ))

(define (run)
  (calc (parse (read))))