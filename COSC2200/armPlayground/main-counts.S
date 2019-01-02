/* Main program template for ARM Playground. */
/* Marquette University 2015 */

/* Counts by Liam Fruzyna */
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu */

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

	/* Sum */
	mov r4, #0
	/* Product */
	mov r5, #1
	/* Evens */
	mov r6, #0
	/* Odds */
	mov r7, #0
	/* Non zero entered */
	mov r8, #0

	/* Start Loop*/
	bl loop

        /* End of your program.         */
        /* Function epilogue.           */
        /* Restores the environment from the O/S. */

loop:
	/* Get Entry */
	bl getnum

	/* If it's 0 end the program */
	cmp r0, #0
	beq check

	mov r8, #1

	/* Add to total sum, multiply to total product */
	add r4, r4, r0
	mul r5, r5, r0
	
	/* Check if even */
	tst r0, #1
	beq even
	bne odd

	bl loop

even:
	/* If even add 1 to even count */
	add r6, r6, #1
	bl loop

odd:
	/* If odd add 1 to odd count */
	add r7, r7, #1
	bl loop

check:
	cmp r8, #0
	beq reset
	bne done

reset:
	mov r5, #0

done:
	/* Print out all calculations */
	mov r0, r4
	bl printnum
	mov r0, r5
	bl printnum
	mov r0, r6
	bl printnum
	mov r0, r7
	bl printnum

        add	sp, sp, #32	/* Destroy stack frame.     */
                                /* Pop O/S state from stack.*/
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to O/S.           */
