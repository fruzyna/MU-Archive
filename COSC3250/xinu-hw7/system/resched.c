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
    //pid_typ head, tail;       // idk why this is here...

    oldproc = &proctab[currpid];

    im = disable();

    /* TODO: Add queue aging to the system. */

    #if AGING
    int head, tail, current;
    head = queuehead(readylist);
    tail = queuetail(readylist);
    current = queuetab[head].next;
    while(current != tail)
    {
        queuetab[current].key++;
        current = queuetab[current].next;
    }
    #endif
    
    //done with aging

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
    //kprintf("[%d %d]", oldproc-proctab, newproc-proctab);
#if PREEMPT
    preempt = QUANTUM;          /* reset preemption counter     */
#endif

    ctxsw(&oldproc->regs, &newproc->regs);

    /* The OLD process returns here when resumed. */
    restore(im);
    return OK;
}
