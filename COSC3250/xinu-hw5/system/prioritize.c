#include <xinu.h>

qid_typ prioritize(pid_typ pid, qid_typ q, ulong key)
{
	queuetab[pid].key = key;

	pid_typ head = queuehead(q);
	pid_typ tail = queuetail(q);

	pid_typ current = head;
	pid_typ prev = head;

	//parse through the whole queue
	while(current != tail)
	{
		//move to the next item
		current = queuetab[current].next;

		//if the queue is not empty
    	if(!(current == tail && prev == head))
		{
			//if the current key is higher or equal priority to this key
			if(queuetab[current].key >= key)
			{
				//if this is the first item we're looking at or the current key is the closest we've seen so far
				if(prev == head || queuetab[current].key <= queuetab[prev].key)
				{
					//set the pid to add after to the current pid
					prev = current;
				}
			}
		}
	}

	//puts the process after and before the appropriate processes
	pid_typ next = queuetab[prev].next;
	queuetab[prev].next = pid;
	queuetab[next].prev = pid;
	queuetab[pid].prev = prev;
	queuetab[pid].next = next;

	return q;
}
