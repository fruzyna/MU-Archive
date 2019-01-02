#lang plai
;Liam Fruzyna
;COSC 3410
;Assignment 5

(define-type JOE
  [id (name symbol?)]
  [num (n number?)]
  [add (lhs JOE?) (rhs JOE?)]
  [sub (lhs JOE?) (rhs JOE?)]
  [mul (lhs JOE?) (rhs JOE?)]
  [div (lhs JOE?) (rhs JOE?)]
  [eql? (lhs JOE?) (rhs JOE?)]
  [lt? (lhs JOE?) (rhs JOE?)]
  [if? (cond JOE?) (tru JOE?) (fal JOE?)]
  [fun (name symbol?) (body JOE?)]
  [app (expr JOE?) (arg JOE?)])

(define-type JOE-value
  [numV (n number?)]
  [booleanV (b boolean?)]
  [closureV (param symbol?)
            (body JOE?)
            (ds DefrdSub?)])

(define-type DefrdSub
  [mtSub]
  [aSub (name symbol?)
        (value JOE-value?)
        (ds DefrdSub?)])

(define (lookup name ds)
  (type-case DefrdSub ds
    [mtSub () (error 'lookup "no binding found for id ~a" name)]
    [aSub (bound-name bound-value rest-ds)
          (if (symbol=? name bound-name)
              bound-value
              (lookup name rest-ds))]))

; interpN: number ds --> numV-n
; interprets a value and returns it as a numV-n
(define (interpN n ds)
  (numV-n (interp n ds)))

; interpB: boolean ds --> booleanV-b
; interprets a boolean and returns it as a booleanV-b
(define (interpB b ds)
  (booleanV-b (interp b ds)))

; calc: function-type left right ds --> numV
; calculates a value using the given funtion
(define (calc fun l r ds)
  (numV (fun (interpN l ds) (interpN r ds))))

; check: comparison-type left right ds --> numV
; checks a value using the given comparision
(define (check fun l r ds)
  (booleanV (fun (interpN l ds) (interpN r ds))))

(define (interp expr ds)
  (type-case JOE expr
    [num (n) (numV n)]
    [id (v) (lookup v ds)]
    [add (l r) (calc + l r ds)]
    [sub (l r) (calc - l r ds)]
    [mul (l r) (calc * l r ds)]
    [div (l r) (calc quotient l r ds)]
    [eql? (l r) (check equal? l r ds)]
    [lt? (l r) (check < l r ds)]
    [if? (c t f) (cond [(interp c ds) (interp t ds)]
                      [else (interp f ds)])]
    [fun (arg body)(closureV arg body ds)]
    [app (fun-expr arg-expr)
         (let ((fun-closure (interp fun-expr ds)))
           (interp (closureV-body fun-closure)
                   (aSub (closureV-param fun-closure)
                         (interp arg-expr ds)
                         (closureV-ds fun-closure))))]))

; valid?: function-type #args full-call --> numV
; determines if the called function uses the given function correctly, error if wrong # args
(define (valid? type args sexp)
  (cond [(and (= (length (cdr sexp)) args) (eq? (first sexp) type)) #t]
        [(eq? (first sexp) type) (error 'parse "Incorrect number of operands for function")]
        [else #f]))

(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(and (= (length (cdr sexp)) 1) (eq? (first sexp) '-)) (sub (num 0) (parse (second sexp)))]
        [(= (length sexp) 2) (app (parse (first sexp))
                                  (parse (second sexp)))]
        [(valid? 'if 3 sexp) (if? (parse (second sexp))
                                  (parse (third sexp))
                                  (parse (fourth sexp)))]
        [(valid? '+ 2 sexp) (add (parse (second sexp))
                                 (parse (third sexp)))]
        [(valid? '- 2 sexp) (sub (parse (second sexp))
                                 (parse (third sexp)))]
        [(valid? '* 2 sexp) (mul (parse (second sexp))
                                 (parse (third sexp)))]
        [(valid? '/ 2 sexp) (div (parse (second sexp))
                                 (parse (third sexp)))]
        [(valid? '< 2 sexp) (lt? (parse (second sexp))
                                 (parse (third sexp)))]
        [(valid? '= 2 sexp) (eql? (parse (second sexp))
                                  (parse (third sexp)))]
        [(valid? 'with 2 sexp) (app (fun (first (second sexp))
                                         (parse (third sexp)))
                                    (parse (second (second sexp))))]
        [(valid? 'fun 2 sexp) (fun (car (second sexp))
                                   (parse (third sexp)))]
        [else (error 'parse "Unknown function call")]))

(define (run expr)
  (interp (parse expr) (mtSub)))