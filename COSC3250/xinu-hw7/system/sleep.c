/**
 * @file sleep.c
 * @provides sleep                                                         
 *
 * COSC 3250 / COEN 4820 Project 7.
 */
/* Embedded XINU, Copyright (C) 2007.  All rights reserved. */

#include <xinu.h>

/**
 * Delay the calling process n milliseconds.
 * @param ms number of milliseconds to sleep
 * @return OK if process is sleeping, SYSERR if no clock
 */
syscall	sleep(int ms)
{
	irqmask ps;

#ifdef RTCLOCK

	//       Put process into sleep state on sleep queue.
	if(ms > 0)
	{
		pcb *currentProc;
		currentProc = &proctab[currpid];
		currentProc->state = PRSLEEP;
		deltainsert(currpid, sleepq, ms);
		resched();
		return OK;
	}
#else
	/* if we have no clock, sleep was an erroneous call */
	return SYSERR;
#endif
}
