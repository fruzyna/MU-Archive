#include <xinu.h>

/**
 * Make a process eligible for CPU service.
 * @param pid process id of the process to move to the ready state
 * @param resch if TRUE, reschedule will be called
 * @return OK if the process has been added to the ready list, else SYSERR
 */
syscall ready(pid_typ pid, bool resch)
{
    register pcb *ppcb;

    ASSERT(!isbadpid(pid));

    ppcb = &proctab[pid];
    ppcb->state = PRREADY;

    //enqueue(pid, readylist);
    prioritize(pid, readylist, ppcb->priority);

    if (resch)
    {
        resched();
    }
    return OK;
}
