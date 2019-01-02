#include <xinu.h>

/**
 * Returns the message sent to the current proccess, does not wait
 */
message recvnow(void)
{
	pcb *proc;
	proc = &proctab[currpid];

	//if there is a message return it
	if(proc->hasMessage)
	{
		proc->hasMessage = FALSE;
		return proc->message;
	}
	else
	{
		//otherwise fail
		return SYSERR;
	}
}

