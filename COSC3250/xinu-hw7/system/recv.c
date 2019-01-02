#include <xinu.h>

/**
 * Returns the message sent to the current proccess
 */
message recv(void)
{
        pcb *proc;
        proc = &proctab[currpid];

		//if there is a message return that
       	if(proc->hasMessage)
       	{
			proc->hasMessage = FALSE;
           	return proc->message;
       	}
       	else
       	{
       		//otherwise wait for a message
       		//kprintf("%d is Waiting\r\n", currpid);
			proc->state = PRRECV;
			resched();
			//kprintf("Returned\r\n");
			proc->hasMessage = FALSE;
			return proc->message;
       	}
}

