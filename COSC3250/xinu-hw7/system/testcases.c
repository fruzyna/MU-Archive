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
/* Embedded XINU, Copyright (C) 2010, 2014.  All rights reserved. */

//TA-BOT:MAILTO liam.fruzyna@marquette.edu Weihua.Liu@Marquette.edu

#include <xinu.h>
    
//process for testing sleeping
void sleepingProc(int sleepTime)
{
	enable();
	//goes to sleep
	kprintf("I am %d\r\n", currpid);
	kprintf("Sleeping for %dms\r\n", sleepTime);
	sleep(sleepTime);
	//wakes up
	kprintf("I am [%d] awake!\n\r", currpid);
}

//process to test recvnow
void recvNow()
{
	//returns output
	kprintf("Got %d\n\r", recvnow());
}

//process to test recv
void recvIt()
{
	enable();
	//returns output
	kprintf("Got %d\n\r", recv());
}

//process to test recvtime
void recvUntil(int sleepTime)
{
	enable();
	//returns output
	kprintf("Got %d\n\r", recv(sleepTime));
}

//process to wait then send
void sleepSend(int pid, int value, int sleepTime)
{
	enable();
	//sleeps
	sleepingProc(sleepTime);
	//sends value
	kprintf("Sending %d to %d\n\r", value, pid);
	send(pid, value);
}

/**
 * testcases - called after initialization completes to test things.
 */
void testcases(void)
{
    int c;
    int pid;
    int sPID;
    	
    kprintf("0) Sleep Process for 3 seconds\r\n");
    kprintf("1) Sleep Process for 1 second and 3 seconds\r\n");
    kprintf("2) Sleep Process for 3 seconds and 1 second\r\n");
    kprintf("3) Send then recvnow\r\n");
    kprintf("4) Recvnow then send\r\n");
    kprintf("5) Recv then send\r\n");
    kprintf("6) Recvsleep then send\r\n");
    kprintf("7) Recvsleep sorter then send\r\n");

    kprintf("===TEST BEGIN===\r\n");

    // TODO: Test your operating system!

    c = kgetc();
    kprintf("Running %c\r\n", c);
    switch (c)
    {
    case '0':
    	ready(create((void *)sleepingProc, INITSTK, 5, "SLP3", 1, 3000), FALSE);
        break;
    case '1':
    	ready(create((void *)sleepingProc, INITSTK, 5, "SLP1", 1, 1000), FALSE);
    	ready(create((void *)sleepingProc, INITSTK, 5, "SLP3", 1, 3000), FALSE);
        break;
    case '2':
    	ready(create((void *)sleepingProc, INITSTK, 5, "SLP3", 1, 3000), FALSE);
    	ready(create((void *)sleepingProc, INITSTK, 5, "SLP1", 1, 1000), FALSE);
        break;
    case '3':
    	pid = create((void *)recvNow, INITSTK, 5, "RNOW", 0);
    	send(pid, 24);
    	ready(pid, FALSE);
    	break;
    case '4':
    	pid = create((void *)recvNow, INITSTK, 5, "RNOW", 0);
    	ready(pid, FALSE);
    	sPID = create((void *)sleepSend, INITSTK, 5, "SEND", 3, pid, 24, 3000);
    	ready(sPID, FALSE);
    	break;
    case '5':
    	pid = create((void *)recvIt, INITSTK, 5, "RECV", 0);
    	ready(pid, FALSE);
    	sPID = create((void *)sleepSend, INITSTK, 5, "SEND", 3, pid, 24, 3000);
    	ready(sPID, FALSE);
    	break;
    case '6':
    	pid = create((void *)recvUntil, INITSTK, 5, "RUNT", 1, 3000);
    	ready(pid, FALSE);
    	sPID = create((void *)sleepSend, INITSTK, 5, "SEND", 3, pid, 24, 1000);
    	ready(sPID, FALSE);
    	break;
    case '7':
    	pid = create((void *)recvUntil, INITSTK, 5, "RUNT", 1, 1000);
    	ready(pid, FALSE);
    	sPID = create((void *)sleepSend, INITSTK, 5, "SEND", 3, pid, 24, 3000);
    	ready(sPID, FALSE);
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
