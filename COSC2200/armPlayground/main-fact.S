/* Main program template for ARM Playground. */
/* Marquette University 2015 */

/* Factorials by Liam Fruzyna */
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

	/* Get the starting number */
	bl getnum
	
	/* Prepare space for starting number and running total */
	mov r4, #1
	mov r5, r0
	add r6, r5, #0

	/* Start loop */
	bl loop

	/* Move final result to r0 */
	mov r0, r6

	/* Display Result */
	bl printnum

        /* End of your program.         */
        /* Function epilogue.           */
        /* Restores the environment from the O/S. */
loop:
	/* Check if the adding is greater than 1 */
	cmp r5, #1
	bgt next

	cmp r5, #2
	blt fin

next:
	/* Subtract 1 from current point */
	sub r5, r5, r4
	/* Multiply running total by current point */
	mul r6, r6, r5
	/* Running total */
	bl loop

fin:
	/*Check that the final answer is not 0 */
	cmp r6, #0
	beq fix
	bne done

fix:
	mov r6, #1

done:
	/* Display Result */
	mov r0, r6
	bl printnum

        add	sp, sp, #32	/* Destroy stack frame.     */
                                /* Pop O/S state from stack.*/
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to O/S.           */
