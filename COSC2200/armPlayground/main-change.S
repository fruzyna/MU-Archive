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
	/* Take in amount in cents */
	bl	getInt          /* Get an integer */
	mov	r7, r0
	cmp	r1, #1          /* Check r1 for EOF flag */
	beq	done
	
	ldr	r0, =CHANGE
	bl	kprintf

	/* If its zero skip to that */
	cmp	r7, #0
	beq	zero

	/* Calc Dollars */
	mov	r0, r7
	mov	r1, #100
	bl	divide
	mov	r4, r1
	mov	r5, r0
	cmp	r5, #1
	beq	dollar
	bne	dollars

	b	mainloop
	/* End of your program.         */
	/* Function epilogue.           */
	/* Restores the environment from the O/S. */

zero:	
	/* Output zero for everything */
	mov	r1, #0
	ldr	r0, =DOLLARS
	bl	kprintf
	ldr	r0, =QUARTERS
	mov	r1, #0
	bl	kprintf
	ldr	r0, =DIMES
	mov	r1, #0
	bl	kprintf
	ldr	r0, =NICKELS
	mov	r1, #0
	bl	kprintf
	ldr	r0, =PENNIES
	mov	r1, #0
	bl	kprintf
	bl	mainloop

dollar:
	/* Output one dollar */
	mov	r1, r5
	ldr	r0, =DOLLAR
	bl	kprintf
	b	countQuarter

dollars:
	/* Output r5 dollars */
	mov	r1, r5
	ldr	r0, =DOLLARS
	bl	kprintf
	b	countQuarter

countQuarter:
	/* Calc Quarters */
	mov	r0, r4
	mov	r1, #25
	bl	divide
	mov	r4, r1
	mov	r5, r0
	cmp	r5, #1
	beq	quarter
	bne	quarters

quarter:
	/* Output one quarter */
	mov	r1, r5
	ldr	r0, =QUARTER
	bl	kprintf
	b	countDime

quarters:
	/* Output r5 quarters */
	mov	r1, r5
	ldr	r0, =QUARTERS
	bl	kprintf
	b	countDime

countDime:
	/* Calc dimes */
	mov	r0, r4
	mov	r1, #10
	bl	divide
	mov	r4, r1
	mov	r5, r0
	cmp	r5, #1
	beq	dime
	bne	dimes

dime:
	/* Output one dime */
	mov	r1, r5
	ldr	r0, =DIME
	bl	kprintf
	b	countNickel

dimes:
	/* Ouput r5 dimes */
	mov	r1, r5
	ldr	r0, =DIMES
	bl	kprintf
	b	countNickel

countNickel:
	/* Calc nickels */
	mov	r0, r4
	mov	r1, #5
	bl	divide
	mov	r4, r1
	mov	r5, r0
	cmp	r5, #1
	beq	nickel
	bne	nickels

nickel:
	/* Output one nickel */
	mov	r1, r5
	ldr	r0, =NICKEL
	bl	kprintf
	b	countPenny

nickels:
	/* Output r5 nickels */
	mov r1, r5
	ldr	r0, =NICKELS
	bl	kprintf
	b	countPenny

countPenny:
	/* Calc pennies */
	mov	r5, r4
	cmp	r5, #1
	beq	penny
	bne	pennies

penny:
	/* Output one penny */
	mov	r1, r5
	ldr	r0, =PENNY
	bl	kprintf
	b	mainloop

pennies:
	/* Output r5 pennies */
	mov	r1, r5
	ldr	r0, =PENNIES
	bl	kprintf
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
WELCOME:        .asciz "Enter numbers to test getInt; Ctrl-D to stop.\r\n"
FORMAT:         .asciz "getInt returned %10d\r\n"
DOLLAR:    		.asciz "%d dollar,\r\n"
QUARTER:        .asciz "%d quarter,\r\n"
DIME:         	.asciz "%d dime,\r\n"
NICKEL:         .asciz "%d nickel,\r\n"
PENNY:         	.asciz "%d penny.\r\n"
DOLLARS:    	.asciz "%d dollars,\r\n"
QUARTERS:       .asciz "%d quarters,\r\n"
DIMES:         	.asciz "%d dimes,\r\n"
NICKELS:        .asciz "%d nickels,\r\n"
PENNIES:        .asciz "%d pennies.\r\n"
CHANGE:		.asciz "Change:\r\n"
                /* This FORMAT string tells kprintf to print a  */
                /*  base-10 signed integers (%d) in a field ten */
                /*  characters wide.                            */

