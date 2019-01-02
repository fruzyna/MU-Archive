/**
 * @file     ctxsw.s
 * @provides ctxsw
 *
 * COSC 3250 / COEN 4820 Assignment 4
 */
/* Embedded XINU, Copyright (C) 2013.  All rights reserved. */

#include <arm.h>

.text
	.align 4
	.globl	ctxsw


/**
 * @fn void ctxsw(&oldregs, &newregs)
 *
 * Switch context (values in registers) to another process, saving the
 * current processes information. This function will not return as normally
 * thought as it will load in the stack pointer for a different process and
 * jump to that location and begin executing code.
 *
 * @param  &oldregs address of outgoing register save area
 * @param  &newregs address of incoming register save area
 * @return special case -- see above
 */
ctxsw:

// TODO: Save callee-save ("non-volatile") registers.
add r0, r0, #16
str r4, [r0]
add r0, r0, #4
str r5, [r0]
add r0, r0, #4
str r6, [r0]
add r0, r0, #4
str r7, [r0]
add r0, r0, #4
str r8, [r0]
add r0, r0, #4
str r9, [r0]
add r0, r0, #4
str r10, [r0]
add r0, r0, #4
str r11, [r0]
add r0, r0, #4
str ip, [r0]
add r0, r0, #4
str sp, [r0]
add r0, r0, #4
str lr, [r0]
add r0, r0, #4
str lr, [r0]

// TODO: Restore callee-save ("non-volatile") registers.
add r1, r1, #16
ldr r4, [r1]
add r1, r1, #4
ldr r5, [r1]
add r1, r1, #4
ldr r6, [r1]
add r1, r1, #4
ldr r7, [r1]
add r1, r1, #4
ldr r8, [r1]
add r1, r1, #4
ldr r9, [r1]
add r1, r1, #4
ldr r10, [r1]
add r1, r1, #4
ldr r11, [r1]
add r1, r1, #4
ldr ip, [r1]
add r1, r1, #4
ldr sp, [r1]
add r1, r1, #4
ldr lr, [r1]
add r1, r1, #4
ldr pc, [r1]

// TODO: Jump to next function.
bx pc;

	//bx	lr

	.end ctxsw
