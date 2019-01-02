/* Main program template for ARM Playground. */
/* Marquette University 2015 */

/* Least Common Multiple by Liam Fruzyna and Scott Stewart*/
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquette.edu*/

#include <arm.h>

/* Main program starts here.  Put nothing before _main_:.  */
.section .text
.globl _main_
_main_:
        /* Function prolog.                                  */
        /* Sets up environment for user program to execute.  */
                                /* Push O/S state on Stack.  */
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
	sub	sp, sp, #32	/* Make stack frame.  */
        /* Function prolog ends. */
        /* Start your program.   */

mainloop:
	/* Gets a first number */
	bl	getInt
	cmp	r1, #1
	beq	done

	/* If it's less than zero reget it */
	cmp	r0, #0
	blt	mainloop
	
	mov	r4, r0

getIntTwo:
	/* Gets a seconds number */
	bl	getInt		
	mov	r5, r0

	/* If that's less than 0 reget it */
	cmp	r0, #0
	blt	getIntTwo
	
	/* If either are 0 return 0 */
	beq	zero
	cmp	r4,#0
	beq	zero

	/* Escape */
	cmp	r1, #1
	beq	done

	mov	r6, r5
	mov	r7, r4
	
	b	gcdLoop

zero:	
	mov	r1,r4
	mov	r2,r0
	mov 	r3,#0
	ldr	r0, =OUT
	bl 	kprintf
	b 	mainloop

gcdLoop:
	cmp	r6, #0
	ble	lcm

	/* Divide r4 by r6 */	
	mov	r0, r7
	mov	r1, r6
	bl	divide

	/* Update r6 to the remainder after r7 to r6 */
	mov	r7, r6
	mov	r6, r1

	b	gcdLoop
	
lcm:
	mov	r0, r5
	mov	r1, r7
	bl	divide
	
	mul	r6, r4, r0

finish:
	mov	r1, r4
	mov	r2, r5
	mov	r3, r6

	/* Print out the both inputs and the result */
	ldr	r0, =OUT
	bl	kprintf

	/* Ask for another */
	b	mainloop

done:
        add     sp, sp, #32     /* Destroy stack frame.     */
                                /* Pop O/S state from stack.*/
        pop     {r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to O/S.           */
/* getInt function returns positive and negative integers */
/*  read in as ASCII characters representing base-10 values.  */
/*  Returns:    */
/*      r0 = integer */
/*      r1 = End Of File (EOF) flag, 1 (true) or 0 (false). */
#define TOTAL   r4
#define DIGIT   r5
#define SIGN    r6
#define SAW_EOF r7

getInt:
        push    {r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}

        mov     r0, #'?'        /* Print a friendly "? " prompt. */
        bl      putchar
        mov     r0, #' '
        bl      putchar
        mov     TOTAL, #0       /* Initialize variables         */
        mov     SIGN, #0
        mov     SAW_EOF, #0

        bl      getchar         /* Get first character          */
        cmp     r0, #'-'        /* IF it is a minus sign,       */
        bne     gi2             /*  THEN set SIGN = 1,          */
        mov     SIGN, #1        /*  ELSE jump into main loop.   */
        bl      putchar         /* Echo minus sign.             */

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
        mov     DIGIT, r0
        bl      putchar         /* Echo digit                   */

        sub     r0, DIGIT, #'0' /* Horner's Method to calculate */
        mov     r1, #10         /*  value of number             */
        mul     TOTAL, TOTAL, r1
        add     TOTAL, TOTAL, r0
        b       giloop

gieof:  mov     SAW_EOF, #1     /* Special case EOF flag = true */
gidone: 
        cmp     SIGN, #1        /* IF sign = 1, subtract total  */
        bne     gid2            /*  from zero to get negative   */
        mov     r1, #0
        sub     TOTAL, r1, TOTAL
gid2:
        mov     r0, #'\r'       /* Echo newline and carriage return */
        bl      putchar
        mov     r0, #'\n'
        bl      putchar
        mov     r0, TOTAL       /* Stage return values          */
        mov     r1, SAW_EOF

        pop     {r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to caller             */

        

/* .data section is for non-code entities, like format strings. */
.section .data
OUT:        .asciz "LCM of %d and %d is %d\r\n"
                /* This FORMAT string tells kprintf to print a  */
                /*  base-10 signed integers (%d) in a field ten */
                /*  characters wide.                            */

