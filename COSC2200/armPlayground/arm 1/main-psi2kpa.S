/* Main program template for ARM Playground. */
/* Marquette University 2015 */

/* PSI to KPA by Liam Fruzyna */
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu*/

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

	/* Get original PSI measurement */
	bl getnum
	
	/* Multiply by 6985 to get to Pascalish */
	mov r4, r0
	mov r5, #68
	mov r1, #10
	mul r5, r5, r1
	add r5, r5, #9
	mul r5, r5, r1
	add r5, r5, #5
	mul r0, r4, r5

	/* Divide by 1000 to get to Kilopascals */
	mov r1, #1000
	bl divide

	/* Display Result */
	bl printnum

        /* End of your program.         */
        /* Function epilogue.           */
        /* Restores the environment from the O/S. */
done:
        add	sp, sp, #32	/* Destroy stack frame.     */
                                /* Pop O/S state from stack.*/
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
        bx      lr              /* Return to O/S.           */
