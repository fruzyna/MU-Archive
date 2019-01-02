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
str r4, [r0, #16]
str r5, [r0, #20]
str r6, [r0, #24]
str r7, [r0, #28]
str r8, [r0, #32]
str r9, [r0, #36]
str r10, [r0, #40]
str r11, [r0, #44]
str sp, [r0, #52]
str lr, [r0, #56]
str lr, [r0, #60]

// TODO: Restore callee-save ("non-volatile") registers.
mov ip, r1
ldr r0, [ip]
ldr r1, [ip, #4]
ldr r2, [ip, #8]
ldr r3, [ip, #12]
ldr r4, [ip, #16]
ldr r5, [ip, #20]
ldr r6, [ip, #24]
ldr r7, [ip, #28]
ldr r8, [ip, #32]
ldr r9, [ip, #36]
ldr r10, [ip, #40]
ldr r11, [ip, #44]
ldr sp, [ip, #52]
ldr lr, [ip, #56]
ldr pc, [ip, #60]

	.end ctxsw
