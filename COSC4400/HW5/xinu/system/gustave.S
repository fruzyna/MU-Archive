	.cpu arm1176jzf-s
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 1
	.eabi_attribute 30, 6
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"gustave.S"
	.global	__aeabi_idiv
	.section	.text.gustave,"ax",%progbits
	.align	2
	.global	gustave
	.syntax unified
	.arm
	.fpu softvfp
	.type	gustave, %function
gustave:
	@ args = 0, pretend = 0, frame = 16
	@ frame_needed = 1, uses_anonymous_args = 0
	push	{fp, lr}
	add	fp, sp, #4
	sub	sp, sp, #16
	ldr	r0, =F_intro
	bl	kprintf

