/* Main program template for ARM Playground. */
/* Marquette University 2015 */

/* ASCII Table by Liam Fruzyna and Scott Stewart*/
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
	/* Get a starting number */
	ldr	r0, =LOW
	bl	kprintf
	bl	getInt
	mov	r4, r0

	/* And an ending number */
	ldr	r0, =HIGH
	bl	kprintf
	bl	getInt
	mov	r5, r0
	
	b	loop

loop:
	/* If the second number smaller than the first, quit */
	cmp	r5, r4
	blt	done

	/* Or if one is bigger than the max */
	cmp	r4, #255
	bgt	done

	/* Or smaller than 0 */
	cmp	r4, #0
	bge	cont
	mov 	r4, #0

cont:
	/* Print as a decimal number */
	mov	r1, r4
	ldr	r0, =DEC
	bl	kprintf

	cmp	r4, #16
	blt	printLeading

	/* Print as a hex number */
	mov	r1, r4
	ldr	r0, =HEX
	bl	kprintf
	b	binary

printLeading:
	mov	r1, r4
	ldr	r0, =SMHEX
	bl	kprintf

binary:
	/* Print as a binary number */
	mov	r1, r4
	ldr	r0, =BIN
	bl	kprintf
	b	calcBin

binReturn:
	mov 	r4, r11
	mov 	r5, r10

	/* Print as an ascii char */
	mov	r1, r4
	ldr	r0, =CHAR
	bl	kprintf

endLoop:
	/* Go to next value */
	add	r4, r4, #1

	b	loop

calcBin:
	/* Start with 128 to calculate binary */
	mov 	r11, r4
	mov 	r10, r5
	mov	r9, #128
	b	binLoop

binLoop:
	/* See how many fit */
	cmp	r4, r9
	mov	r6, #0
	blt	skipTo
	sub	r5, r4, r9
	mov 	r4, r5
	mov	r6, #1

skipTo:
	/* Output found value */
	mov	r1, r6
	ldr	r0,=BINARY
	bl	kprintf	

	/* Divide position by 2 to get net number right */
	mov	r0, r9
	mov	r1, #2
	bl	divide

	/* If its the last number go to ascii, otherwise go to next binary number */
	cmp	r9, #1
	mov	r9, r0
	bne	binLoop
	b 	binReturn

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
LOW:        .asciz "low"
HIGH:         .asciz "high"
HEX:		.asciz "	0x%X"
SMHEX:		.asciz "	0x0%X"
DEC:		.asciz " %d"
BIN:		.asciz "	0b"
CHAR:		.asciz "	%c\r\n"
BINARY:		.asciz "%d"
                /* This FORMAT string tells kprintf to print a  */
                /*  base-10 signed integers (%d) in a field ten */
                /*  characters wide.                            */

