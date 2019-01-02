/**
 * @file testcases.c
 * @provides testcases
 *
 * $Id: testcases.c 175 2008-01-30 01:18:27Z brylow $
 *
 * Modified by:
 *
 * and
 *
 */
/* Embedded XINU, Copyright (C) 2007.  All rights reserved. */

//TA-BOT:MAILTO liam.fruzyna@marquette.edu matthew.kinzler@marquette.edu

#include <xinu.h>


void bigargs(int a, int b, int c, int d, int e, int f)
{
    kprintf(" bigargs(%d, %d, %d, %d, %d, %d) == %d\r\n",
            a, b, c, d, e, f, a + b + c + d + e + f);
}

void printq(qid_typ q)
{
	pid_typ head = queuehead(q);
	pid_typ tail = queuetail(q);

	kprintf("Queue\n\rhead>");
	pid_typ current = head;
	while(current != tail)
  	{
      current = queuetab[current].next;
      if(current < 50)
      {
        kprintf("%d:%d>", current, queuetab[current].key);
      }
  	}
  	kprintf("tail\n\r");
}

void printpid(int times)
{
    int i = 0;

    enable();
    for (i = 0; i < times; i++)
    {
        kprintf("This is process %d\r\n", currpid);
        resched();
    }
}

/**
 * testcases - called after initialization completes to test things.
 */
void testcases(void)
{
    int c;

    kprintf("===TEST BEGIN===\r\n");

    kprintf("0) Test priority scheduling\r\n");
    kprintf("1) 1 process\r\n");
    kprintf("2) 2 higher first process\r\n");
    kprintf("3) 2 lower first process\r\n");
    kprintf("4) 2 same process\r\n");


    // Test your operating system!

    c = kgetc();
    switch (c)
    {
    case '0':
        ready(create((void *)printpid, INITSTK, 2, "PRINTER-A", 1, 5), 0);
        ready(create((void *)printpid, INITSTK, 5, "PRINTER-B", 1, 5), 0);
        ready(create((void *)printpid, INITSTK, 10, "PRINTER-C", 1, 5),
              0);
        ready(create((void *)printpid, INITSTK, 5, "PRINTER-D", 1, 5), 0);

        ready(create
              ((void *)bigargs, INITSTK, 20, "BIGARGS", 6, 10, 20, 30, 40,
               50, 60), 0);
        break;
	case '1':
		//create 1 process and prioritize
		prioritize(create((void *)printpid, INITSTK, 2, "PRINTER-A", 1, 5), toyreadylist, 2);
		printq(toyreadylist);
		break;
	case '2':
		//create 2 processes and prioritize
		prioritize(create((void *)printpid, INITSTK, 2, "PRINTER-A", 1, 5), toyreadylist, 2);
		prioritize(create((void *)printpid, INITSTK, 1, "PRINTER-A", 1, 5), toyreadylist, 1);
		printq(toyreadylist);
		break;
	case '3':
		//create 2 processes and prioritize
		prioritize(create((void *)printpid, INITSTK, 1, "PRINTER-A", 1, 5), toyreadylist, 1);
		prioritize(create((void *)printpid, INITSTK, 2, "PRINTER-A", 1, 5), toyreadylist, 2);
		printq(toyreadylist);
		break;
	case '4':
		//create 2 processes and prioritize
		prioritize(create((void *)printpid, INITSTK, 1, "PRINTER-A", 1, 5), toyreadylist, 1);
		prioritize(create((void *)printpid, INITSTK, 1, "PRINTER-A", 1, 5), toyreadylist, 1);
		printq(toyreadylist);
		break;
    default:
        break;
    }

    while (numproc > 2)
    {
        resched();
    }
    kprintf("\r\n===TEST END===\r\n");
    return;
}
