/* Phone Codes by Liam Fruzyna and Scott Stewart*/
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquette.edu*/

#include <arm.h>

.section .text
.globl _main_

#define	digitsArray	r4
#define	answerArray	r5
#define	numDigits	r6
#define	pointerDigit	r7

_main_:
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    	sub	sp, sp, #32

fillLoop:

	mov	numDigits, #16
	mov	pointerDigit, #0
	/* Number of bytes */
	mov	r8, #4
	/* Number of bytes needed */
	mul	r9, numDigits, r8

	/* Create digit array */
	mov	r0, r9
	bl	malloc
	mov	digitsArray, r0

	/* Create answer array */
	mov	r0, r9
	bl	malloc
	mov	answerArray, r0

	/* Fill array */
	mov	r0, digitsArray
	bl	getIn

	mov	digitsArray, r0
	mov	numDigits, r1

	/* Repeat until ctrl + d */
	cmp	r2, #1
	beq	done

	/* Send arguments and start looking for matches */
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop

	ldr	r0, =LINE
	bl	kprintf

	/* Loop */
	b	fillLoop

loop:
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}

	/* Store arguments */
	mov	digitsArray, r0
	mov	answerArray, r1
	mov	numDigits, r2
	mov	pointerDigit, r3

	/* Check if done */
	mov	r0, answerArray
	mov	r1, numDigits
	mov	r2, pointerDigit
	cmp	pointerDigit, numDigits
	bge	finish

	/* Load in the current digit */
	mov	r8, #4
	mul	r8, r8, pointerDigit
	ldr	r9, [digitsArray, r8]

	/* Compare digit across all options */
	cmp	r9, #'0'
	beq	other

	cmp	r9, #'1'
	beq	other

	cmp	r9, #'2'
	beq	two

	cmp	r9, #'3'
	beq	three

	cmp	r9, #'4'
	beq	four

	cmp	r9, #'5'
	beq	five

	cmp	r9, #'6'
	beq	six

	cmp	r9, #'7'
	beq	seven

	cmp	r9, #'8'
	beq	eight

	cmp	r9, #'9'
	beq	nine

	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
	
other:

	/* Use the given char */
	str	r9, [answerArray, r8]

	/* Send parameters into loop */
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop

	/* Return from loop and return to last recursion point */
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr

two:
	/* Use 'A' */
	mov	r0, #65
	str	r0, [answerArray, r8]

	/* Send parameters into loop */
	add	pointerDigit, pointerDigit, #1
	
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop

	
	/* Return from loop */
	sub	pointerDigit, pointerDigit, #1

	/* Use 'B' */
	mov	r0, #66
	str	r0, [answerArray, r8]

	/* Send parameters into loop */
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	
	/* Return from loop */
	sub	pointerDigit, pointerDigit, #1

	/* Use 'C' */
	mov	r0, #67
	str	r0, [answerArray, r8]

	/* Send parameters into loop */
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop

	/* Return from loop and return to last recursion point */
	sub	pointerDigit, pointerDigit, #1
	
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
three:
	mov	r9, #68
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #69
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #70
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
four:
	mov	r9, #71
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #72
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #73
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
five:
	mov	r9, #74
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #75
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #76
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
six:
	mov	r9, #77
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #78
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #79
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
seven:
	mov	r9, #80
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #81
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #82
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #83
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
eight:
	mov	r9, #84
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #85
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #86
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
nine:
	mov	r9, #87
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #88
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #89
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	mov	r9, #90
	str	r9, [answerArray, r8]
	add	pointerDigit, pointerDigit, #1
	mov	r0, digitsArray
	mov	r1, answerArray
	mov	r2, numDigits
	mov	r3, pointerDigit
	bl	loop
	sub	pointerDigit, pointerDigit, #1
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr

finish:
	/* Loop to print out all options */
	mov	r9, #0
	mov	answerArray, r0
	mov	numDigits, r1
	mov	pointerDigit, r2

finLoop:
	/* Check if end of for loop */
	cmp	r9, pointerDigit
	bge	end

	/* Get answer at position */
	mov	r8, #4
	mul	r8, r8, r9
	ldr	r1, [answerArray, r8]

	/* Print answer */
	ldr	r0, =PRINT
	bl	kprintf

	/* Increment */
	add	r9, r9, #1
	b	finLoop

end:
	ldr	r0, =TAB
	bl	kprintf
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr

done:
	add	sp, sp, #32
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    	bx	lr

getIn:
        push    {r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}

	mov	digitsArray, r0
	mov	numDigits, #0

        mov     r0, #'?'        /* Print a friendly "? " prompt. */
        bl      putchar
        mov     r0, #' '
        bl      putchar

        mov     r9, #0

giloop:
        bl      getchar         /* Get first possible digit.    */
gi2:    cmp     r0, #4          /* If EOF, exit on special case */
        beq     gieof
        cmp     r0, #'\n'       /* If newline, then done        */
        beq     gidone          /*  reading digits.             */
        cmp     r0, #'0'        /* Check for digits between '0' */
        blt     giloop          /*  and '9'.                    */
        cmp     r0, #'9'
        bgt     giloop          /* Otherwise, ignore character  */
        mov     r5, r0
        bl      putchar         /* Echo digit                   */

	mov	r8, #4
	mul	r2, numDigits, r8
	str	r5, [digitsArray, r2]

	add	numDigits, numDigits, #1
        b       giloop

gieof:  mov     r9, #1     /* Special case EOF flag = true */
gidone: 
        mov     r0, #'\r'       /* Echo newline and carriage return */
        bl      putchar
        mov     r0, #'\n'
        bl      putchar
        mov     r0, digitsArray
        mov     r1, numDigits
	mov	r2, r9

        pop     {r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to caller             */

.section .data
PRINT:	.asciz "%c"
TAB:	.asciz " "
LINE:	.asciz "\r\n"
CHECK:	.asciz "Check %d \r\n"
DONE:	.asciz "Done \r\n"

