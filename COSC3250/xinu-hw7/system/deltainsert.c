/**
 * @file deltainsert.c
 * @provides deltainsert
 *
 * COSC 3250 / COEN 4820 Project 7.
 */
/* Embedded XINU, Copyright (C) 2007.  All rights reserved. */

#include <xinu.h>

/**
 * Insert a process in delta queue in ascending order
 * @param pid    process id to insert
 * @param q      queue in which process should be inserted
 * @param key    delta key
 * @return OK
 */
short deltainsert(int pid, qid_typ q, short key)
{
	disable();
	short next;         /* runs through list                  */
	short prev;         /* follows next through list          */
	short head, tail;
	short current;
		
	head = queuehead(q);
	tail = queuetail(q);

	/*
	kprintf("Delta inserting %d for %d\r\n", pid, key);

	kprintf("\r\nSleep Q\r\n");
	for(current = head; current != tail; current = queuetab[current].next)
	{
		kprintf("PID %d KEY %d\r\n", current, queuetab[current].key);
	}
*/

	//if empty just put right in
	if(queuetab[head].next == tail)
	{
		queuetab[head].next = pid;
		queuetab[pid].prev = head;
		queuetab[pid].key = key;
		queuetab[pid].next = tail;
		queuetab[tail].prev = pid;
	}
	else
	{		
		//if not empty find where to place
		int i = head;
		while(i != tail)
		{
			//next in queue
			i = queuetab[i].next;
			
			//if at the end or less wait than next add before next
			if(key < queuetab[i].key || i == tail)
			{
				//kprintf("Delta inserting %d for %d\r\n", pid, key);
				prev = queuetab[i].prev;
				next = i;
				queuetab[prev].next = pid;
    	       	queuetab[pid].prev = prev;
    	       	queuetab[pid].key = key;
    	       	queuetab[pid].next = next;
    	       	queuetab[next].prev = pid;
    	       	if(i != tail)
    	       	{
    	       		//subtract key from next item
    	       		queuetab[next].key -= key;
    	       	}
    	       	break;
			}
			else
			{
				//subtract current key from new item
				key -= queuetab[i].key;
			}
		}
	}
	/*
	for(current = head; current != tail; current = queuetab[current].next)
	{
		kprintf("\r\nSleep Q\r\n");
		kprintf("PID %d KEY %d\r\n", pid, key);
	}*/
	enable();
	return OK;
}
