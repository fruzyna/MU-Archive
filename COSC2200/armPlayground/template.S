/* PSI to KPA by Liam Fruzyna and Scott Stewart*/
/* TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquett.edu*/

#include <arm.h>

.section .text
.globl _main_
_main_:
	push	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    sub	sp, sp, #32

done:
    add	sp, sp, #32
	pop	{r4, r5, r6, r7, r8, r9, sl, fp, ip, lr}
    bx	lr
