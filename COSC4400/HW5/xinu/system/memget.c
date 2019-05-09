/**
 * @file memget.c
 * @provides memget                                                       
 *
 * COSC 3250 / COEN 4820 malloc project.
 */
/* Embedded XINU, Copyright (C) 2009.  All rights reserved. */

#include <xinu.h>

/**
 * Allocate heap storage, returning pointer to assigned memory region.
 * @param nbytes number of bytes requested
 * @return pointer to region on success, SYSERR on failure
 */
void *memget(uint nbytes)
{
	// TODO: Search free list for first chunk large enough to fit.
	//       Break off region of requested size; return pointer
	//       to new memory region, and add any remaining chunk
	//       back into the free list.
    if (nbytes <= 0)
    {
        return (void *)SYSERR;
    }
    nbytes = (uint)roundmb(nbytes);
    memblk * curr = freelist.next;
    memblk * prev = NULL;
    while(1)
    {
        if (curr == NULL)
        {
            return (void *)SYSERR;
        }
        if (curr->length < nbytes)
        {
            prev = curr;
            curr = curr->next;
        }
        else
        {
            memblk * block = (memblk *)(curr + nbytes/8);
            block->next = curr->next;
            block->length = curr->length - nbytes;
            if (block->length <= 0)
            {
                block = curr->next;
            }
            if (prev == NULL)
            {
                freelist.next = block;
            }
            else
            {
                prev->next = block;
            }
            freelist.length -= nbytes;
            return curr;
        }
    }
    return (void *)SYSERR;
}
