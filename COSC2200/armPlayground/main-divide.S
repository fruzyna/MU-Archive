/* PSI to KPA by Liam Fruzyna and Scott Stewart*/
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquette.edu*/

#include <arm.h>

.section .text
.globl _main_
_main_:
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    sub	sp, sp, #32

    bl	getnum
    mov	r4, r0
    bl	getnum
    mov	r1, r0
    mov	r0, r4

    bl	mydivide

    mov r4,r1
    bl	printnum
    mov	r0, r4
    bl	printnum

   
    mov r1, #0
    sub r0, r1, #12
    mov r1, #5

    bl divide
     mov r4,r1
    bl	printnum
    mov	r0, r4
    bl	printnum

    mov r1, #0
    sub r0, r1, #12
    mov r1, #5

    bl mydivide
     mov r4,r1
    bl	printnum
    mov	r0, r4
    bl	printnum

    b	done

mydivide:
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    mov	r4, r0 /*Dividend*/
    mov	r5, r1 /*Divisor*/
    mov	r6, #0 /*Answer*/
    mov	r7, #0 /*Remainder*/
    mov r8, #1 /*Final Multiplier*/

    cmp	r4,	#0
    bge notNegative1
    sub r8,r8,#2
notNegative1:

	cmp r4, #0
	bne notZero
	mov r6,#0
	mov r7,#0
	b end
notZero:

	cmp r5, #0
	bge notNegative2
	sub r8,r8,#2
	mov r9,#0
	sub r9,r9,#3
	cmp r8,r9
	bne notNegative2
	mov r8,#1
notNegative2:

	cmp r5, #0
	bne notZero2
	mov r6,#0
	mov r7, r5
	b end
notZero2:

loop:
	cmp	r4, #0
	ble	finish

	add	r6, r6, #1
	sub	r4, r4, r5

	b	loop 	

finish:
	cmp	r4, #0
	beq	end

	sub	r6, r6, #1
	add	r7, r4, r5

end:
	mul r6,r6,r8
	mov	r0, r6
	mov	r1, r7
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr

done:
    add	sp, sp, #32	/* Destroy stack frame.     */
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	bx	lr
