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
	ldr	r0, =1
	ldr	r2, =temps
	str	r0, [r2, #0]
	ldr	r0, =F_int
	ldr	r2, =temps
	ldr	r1, [r2, #0]
	bl	kprintf
	bl	M_two
	ldr	r0, =3
	ldr	r2, =temps
	str	r0, [r2, #4]
	ldr	r0, =F_int
	ldr	r2, =temps
	ldr	r1, [r2, #4]
	bl	kprintf
	ldr	r0, =F_close
	bl	kprintf
	mov	r0, r0	@ nop
	sub	sp, fp, #4
	@ sp needed
	pop	{fp, pc}
	.align	2
	.size	gustave, .-gustave
	.ident	"GCC: (GNU Tools for ARM Embedded Processors) 4.9.3 20150529 (release) [ARM/embedded-4_9-branch revision 227977]"
	.section	.rodata
	.align	2
F_intro:
	.ascii	"****Begin Gustave Run****\015\012\000"
	.align	2
F_close:
	.ascii	"\015\012*****End Gustave Run*****\015\012\000"
	.align	2
F_int:
	.ascii	"%d \000"
	.align	2
F_newline:
	.ascii	"\015\012\000"
	.section	.data
	.align 2
V_make:
	.word	0
	.align 2
V_two:
	.word	0
	.align 2
temps:
	.space	8
	.align	2
M_two:
	push	{lr}
	ldr	r0, =2
	ldr	r2, =temps
	str	r0, [r2, #8]
	ldr	r0, =F_int
	ldr	r2, =temps
	ldr	r1, [r2, #8]
	bl	kprintf
	pop	{pc}
	.align	2
