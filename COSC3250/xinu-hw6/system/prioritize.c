#include <xinu.h>

qid_typ prioritize(pid_typ pid, qid_typ q, ulong key)
{
    //enqueue(pid, q);

    if (isbadqueue(q) || isbadpid(pid))
    {
        return SYSERR;
    }
    queuetab[pid].key = key;

    pid_typ head, tail, current;
    head = queuehead(q);
    tail = queuetail(q);
    current = queuetab[head].next;
    
    while(key <= queuetab[current].key)
    {
        if(current == tail)
        {
            break;
        }
        current = queuetab[current].next;
    }

    queuetab[pid].next = current;
    queuetab[pid].prev = queuetab[current].prev;
    queuetab[queuetab[current].prev].next = pid;
    queuetab[current].prev = pid;

    return q;
}
