#include <xinu.h>

/**
 * Send a message to a process
 */
syscall send(int pid, int message)
{
	if(pid >= 0 && pid < NPROC)
	{
		pcb *proc;
		proc = &proctab[pid];
		if(proc->hasMessage)
		{
			return SYSERR;
		}
		else
		{
			//kprintf("Sending %d to %d\r\n", message, pid);
			proc->message = message;
			proc->hasMessage = TRUE;
			if(proc->state == PRRECV || proc->state == PRRVTM)
			{
				ready(pid, FALSE);
				//kprintf("Sent\r\n");
				resched();
			}
		}
	}
	return 1;
}
