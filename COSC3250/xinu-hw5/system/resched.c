/**
 * @file resched.c
 * @provides resched
 *
 * COSC 3250 / COEN 4820 Assignment 4
 */
/* Embedded XINU, Copyright (C) 2008.  All rights reserved. */

#include <xinu.h>

extern void ctxsw(void *, void *);
/**
 * Reschedule processor to next ready process.
 * Upon entry, currpid gives current process id.  Proctab[currpid].pstate 
 * gives correct NEXT state for current process if other than PRREADY.
 * @return OK when the process is context switched back
 */
syscall resched(void)
{
    irqmask im;
    pcb *oldproc;               /* pointer to old process entry */
    pcb *newproc;               /* pointer to new process entry */
    pid_typ head, tail;

    oldproc = &proctab[currpid];

    im = disable();

    /* Queue aging */
    head = queuehead(readylist);
    tail = queuetail(readylist);
    
    if(AGING)
    {
        pid_typ current = head;
		//parse through queue
        while(current != tail)
        {
			//increase key value for all non-current processes
            if(current != PRCURR)
            {
                current = queuetab[current].next;
                queuetab[current].key++;
            }
        }
    }

    /* place current process at end of ready queue */
    if (PRCURR == oldproc->state)
    {
        oldproc->state = PRREADY;
        //enqueue(currpid, readylist);
		prioritize(currpid, readylist, oldproc->priority);
    }

    /* remove first process in ready queue */
    currpid = dequeue(readylist);
    newproc = &proctab[currpid];
    newproc->state = PRCURR;    /* mark it currently running    */

#if PREEMPT
    preempt = QUANTUM;          /* reset preemption counter     */
#endif
    ctxsw(&oldproc->regs, &newproc->regs);

    /* The OLD process returns here when resumed. */
    restore(im);
    return OK;
}
