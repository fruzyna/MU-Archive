/**
 * @file wakeup.c
 * @provides wakeup                                                       
 *
 * COSC 3250 / COEN 4820 Project 7.
 */
/* Embedded XINU, Copyright (C) 2007.  All rights reserved. */

#include <xinu.h>

/**
 * Called by clock interrupt dispatcher to awaken processes.
 */
void wakeup(void)
{
	short head = queuehead(sleepq);
	short tail = queuetail(sleepq);
        
	short next, prev;

	short current = queuetab[head].next;
	if(current != tail)
	{
		//subtract one ms from front
		queuetab[current].key--;
		//go through all items
		while(current != tail)
		{
			if(current != head && current != tail)
			{
				//remove all items that are ready
				if(queuetab[current].key <= 0)
				{
					prev = queuetab[current].prev;
					next = queuetab[current].next;
					queuetab[prev].next = next;
					queuetab[next].prev = prev;
					ready(current, FALSE);
					current = next;
				}
				else
				{
					break;
				}
			}
		}
	}
}