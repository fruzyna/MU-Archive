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

//TA-BOT:MAILTO liam.fruzyna@marquette.edu matthew.kinzler@marquette.edu

#include <xinu.h>

ulong rand(void)
{
    ulong a = 1664525UL;
    ulong b = 1013904223UL;
    static ulong c = 0;

    c = a * c + b;
    return c;
}

syscall sleep(int time)
{
    /* Dumbest sleep ever. */
    int i = 0, j = 0;
    for (i = 0; i < time; i++)
        for (j = 0; j < 1000; j++)
        {
        }
    return 0;
}

/* BEGIN Textbook code from Ch 5 Programming Project 3, Silberschatz p. 254 */
typedef int buffer_item;
#define BUFFER_SIZE 5

struct boundedbuffer
{
    buffer_item buffer[BUFFER_SIZE];
    int bufferhead;
    int buffertail;

    semaphore empty;
    semaphore full;
    semaphore mutex;
};

int insert_item(struct boundedbuffer *bb, buffer_item item)
{
    /* insert item into buffer
     * return 0 if successful, otherwise
     * return SYSERR indicating an error condition */
    if(wait(bb->full) == SYSERR)
    {
        return -1;
    }
    if(wait(bb->mutex) == SYSERR)
    {
        return -1;
    }
    bb->buffer[bb->buffertail] = item;
    bb->buffertail = (bb->buffertail+1)%BUFFER_SIZE;
    if(signal(bb->mutex) == SYSERR)
    {
        return -1;
    }
    if(signal(bb->empty) == SYSERR)
    {
        return -1;
    }
    return 0;
}

int remove_item(struct boundedbuffer *bb, buffer_item * item)
{
    /* remove an object from buffer
     * placing it in item
     * return 0 if successful, otherwise
     * return SYSERR indicating an error condition */
    if(wait(bb->empty) == SYSERR)
    {
        return -1;
    }
    if(wait(bb->mutex) == SYSERR)
    {
        return -1;
    }
    *item = bb->buffer[bb->bufferhead];
    bb->bufferhead = (bb->bufferhead+1)%BUFFER_SIZE;
    if(signal(bb->mutex) == SYSERR)
    {
        return -1;
    }
    if(signal(bb->full) == SYSERR)
    {
        return -1;
    }
    return 0;
}

void producer(struct boundedbuffer *bb)
{
    buffer_item item;

    enable();
    while (1)
    {
        /* sleep for a random period of time */
        sleep(rand() % 100);
        /* generate a random number */
        item = rand();
        if (insert_item(bb, item))
            kprintf("report error condition\r\n");
        else
            kprintf("producer %d produced %d\r\n", currpid, item);
    }
}

void consumer(struct boundedbuffer *bb)
{
    buffer_item item;
    enable();
    while (1)
    {
        /* sleep for a random period of time */
        sleep(rand() % 100);
        if (remove_item(bb, &item))
            kprintf("report error condition\r\n");
        else
            kprintf("consumer %d consumed %d\r\n", currpid, item);
    }
}

/* END Textbook code from Ch 5 Programming Project 3, Silberschatz p. 254 */

void printNoMutex(void)
{
    enable();
    char string[] = "This is a really long string to test mutexAcquire() and mutexRelease() I really hope it doesn't break anything. JK it should totally break before I test it using those methods but after it, it should be one nice and long consistant string, hopefully. *Crosses fingers*";
    kprintf("Test One w/o mutex methods\r\n");
    kprintf("%s\r\n", string);
}

void printMutex(void)
{
    enable();
    char string[] = "This is a really long string to test mutexAcquire() and mutexRelease() I really hope it doesn't break anything. JK it should totally break before I test it using those methods but after it, it should be one nice and long consistant string, hopefully. *Crosses fingers*";
    mutexAcquire();
    kprintf("Test Two w/ mutext methods\r\n");
    kprintf("%s\r\n", string);
    mutexRelease();
}

void semOne(semaphore s)
{
    enable();
    wait(s);
    struct sement sem = semtab[s];
    sem.count++;
    kprintf("%d\r\n", sem.count);
    signal(s);
}

void semTwo(semaphore s)
{
    enable();
    wait(s);
    struct sement sem = semtab[s];
    sem.count--;
    kprintf("%d\r\n", sem.count);
    signal(s);
}
    
/**
 * testcases - called after initialization completes to test things.
 */
void testcases(void)
{
    int c;
    struct boundedbuffer bbuff;
    bbuff.full = semcreate(0);
    bbuff.empty = semcreate(BUFFER_SIZE);
    bbuff.mutex = semcreate(1);
    bbuff.bufferhead = 0;
    bbuff.buffertail = 0;
    
    kprintf("Full: %d Empty: %d Mutex: %d\r\n", bbuff.full, bbuff.empty, bbuff.mutex);

    kprintf("0) Test 1 producer, 1 consumer, same priority\r\n");
    kprintf("1) Test kprintf() with and without the mutex\r\n");
    kprintf("2) Test semaphores\r\n");
    kprintf("3) Test kprintf() with 2 of the mutex\r\n");

    kprintf("===TEST BEGIN===\r\n");

    // TODO: Test your operating system!

    c = kgetc();
    switch (c)
    {
    case '0':
        // Initialize bbuff, and create producer and consumer processes
        ready(create((void *)consumer, INITSTK, 2, "PRINTER-A", 1, &bbuff), 0);
        ready(create((void *)producer, INITSTK, 2, "PRINTER-B", 1, &bbuff), 0);
        break;
    case '1':
        ready(create((void *)printNoMutex, INITSTK, 2, "NO-MUTEX-A", 0), 0);
        ready(create((void *)printMutex, INITSTK, 2, "MUTEX-A", 0), 0);
        break;
    case '3':
        ready(create((void *)printMutex, INITSTK, 2, "MUTEX-A", 0), 0);
        ready(create((void *)printMutex, INITSTK, 2, "MUTEX-B", 0), 0);
        break;
    case '2': ;
        semaphore s = semcreate(1);
        ready(create((void *)semOne, INITSTK, 2, "SEM-ONE", 1, s), 0);
        ready(create((void *)semTwo, INITSTK, 2, "SEM-TWO", 1, s), 0);
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
