#include <xinu.h>

/**
 * Returns the message sent to the current proccess, only waits a certain time
 */
message recvtime(int time)
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
		//otherwise wait a certain time
        proc->state = PRRVTM;
		sleep(time);
          
        //if there is then a message return it      
	    if(proc->hasMessage)
        {
			proc->hasMessage = FALSE;
            return proc->message;
        }
		else
		{
			//otherwise give up
			return SYSERR;
		}
    }
}

