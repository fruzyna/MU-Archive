#lang plai
;Liam Fruzyna
;COSC 3410
;Assignment 7

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
  [fun (name symbol?) (type Type?) (body JOE?)]
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

(define-type TypeEnv
  [mtTypeSub]
  [aTypeSub (name symbol?)
        (type Type?)
        (tenv TypeEnv?)])

(define-type Type
  [numType]
  [boolType]
  [funType (domain Type?) (codomain Type?)])

(define (lookup name ds)
  (type-case DefrdSub ds
    [mtSub () (error 'lookup "no binding found for id ~a" name)]
    [aSub (bound-name bound-value rest-ds)
          (if (symbol=? name bound-name)
              bound-value
              (lookup name rest-ds))]))

(define (lookup-type name tenv)
  (type-case TypeEnv tenv
    [mtTypeSub () (error 'lookup-type "no binding found for type ~a" name)]
    [aTypeSub (bound-name bound-type rest-tenv)
          (if (symbol=? name bound-name)
              bound-type
              (lookup-type name rest-tenv))]))

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
    [if? (c t f) (cond [(booleanV-b (interp c ds)) (interp t ds)]
                      [else (interp f ds)])]
    [fun (arg type body)(closureV arg body ds)]
    [app (fun-expr arg-expr)
         (let ((fun-closure (interp fun-expr ds)))
           (interp (closureV-body fun-closure)
                   (aSub (closureV-param fun-closure)
                         (interp arg-expr ds)
                         (closureV-ds fun-closure))))]))

; valid?: function-type #args full-call --> boolean
; determines if the called function uses the given function correctly, error if wrong # args
(define (valid? type args sexp)
  (cond [(and (= (length (cdr sexp)) args) (eq? (first sexp) type)) #t]
        [(eq? (first sexp) type) (error 'parse "Incorrect number of operands for function")]
        [else #f]))

; sub-valid?: function-type #args #sec-args full-call --> boolean
; determines if the first argument has the right amount of sub arguments if is valid
(define (sub-valid? type args sec-args sexp)
  (if (valid? type args sexp) (if (= sec-args (length (second sexp))) #t (error 'parse "Incorrect number of operands for function")) #f))

(define (get-type ast tenv)
  (type-case JOE ast
    [num (n) (numType)]
    [id (v) (lookup-type v tenv)]
    [add (l r) (param-check (and (is-type? l (numType) tenv)
                                 (is-type? r (numType) tenv)) (numType))]
    [sub (l r) (param-check (and (is-type? l (numType) tenv)
                                 (is-type? r (numType) tenv)) (numType))]
    [mul (l r) (param-check (and (is-type? l (numType) tenv)
                                 (is-type? r (numType) tenv)) (numType))]
    [div (l r) (param-check (and (is-type? l (numType) tenv)
                                 (is-type? r (numType) tenv)) (numType))]
    [eql? (l r) (param-check (same-type? l r tenv) (boolType))]
    [lt? (l r) (param-check (and (is-type? l (numType) tenv)
                                 (is-type? r (numType) tenv)) (boolType))]
    [if? (c t f) (param-check (and (is-type? c (boolType) tenv)
                                   (same-type? t f tenv)) (get-type t tenv))]
    [fun (arg type body) (funType type (get-type body (aTypeSub arg type tenv)))]
    [app (fun-expr arg-expr) (let ((fType (get-type fun-expr tenv))
                                   (aType (get-type arg-expr tenv)))
                               (if (equal? aType (funType-domain fType)) (funType-codomain fType) (error 'get-type "Passed value is not of expected type")))]))

; if the parameters check given passes return a given type, otherwise throw an invalid type error
(define (param-check params return)
  (cond [params return]
        [else (error 'param-check "Invalid type!")]))

; returns if a given parameter is an expected type
(define (is-type? param expected tenv)
  (equal? (get-type param tenv) expected))

; returns if 2 given parameters are the same type
(define (same-type? a b tenv)
  (equal? (get-type a tenv) (get-type b tenv)))

; converts an plain english type to a joe type
(define (parse-type type)
  (cond [(equal? type 'number) (numType)]
        [(equal? type 'boolean) (boolType)]
        [else (funType (parse-type (first type)) (parse-type (third type)))]))

(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(and (= (length (cdr sexp)) 1) (eq? (first sexp) '-)) (sub (num 0) (parse (second sexp)))]
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
        [(sub-valid? 'with 2 4 sexp) (app (fun (first (second sexp))
                                               (parse-type (third (second sexp)))
                                               (parse (third sexp)))
                                          (parse (fourth (second sexp))))]
        [(sub-valid? 'fun 2 3 sexp) (fun (first (second sexp))
                                         (parse-type (third (second sexp)))
                                         (parse (third sexp)))]
        [(= (length sexp) 2) (app (parse (first sexp))
                                  (parse (second sexp)))]
        [else (error 'parse "Unknown function call")]))

(define (run expr)
  (interp (parse expr) (mtSub)))

(define (type-check expr)
  (get-type (parse expr) (mtTypeSub)))