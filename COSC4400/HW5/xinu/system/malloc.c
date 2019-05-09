/**
 * @file malloc.c
 * @provides malloc                                                       
 * Kevin Etta
 * COSC 3250 / COEN 4820 malloc project.
 */
/* Embedded XINU, Copyright (C) 2009.  All rights reserved. */

#include <xinu.h>

/**
 * Allocate heap storage plus accounting block, returning pointer to
 * assigned memory region.
 * @param nbytes number of bytes requested
 * @return pointer to region on success, SYSERR on failure
 */
void *malloc(uint nbytes)
{
	// TODO: Pad request size with room for accounting info.
	//       Call memget() to allocate region.
	//       Store accounting block at head of region, including size
	//         of request.  Return pointer to space above accounting
	//         block.
    memblk * block = (memblk *)memget(nbytes+8);
    if ((void *)SYSERR == block)
    {
        return (void *)SYSERR;
    }
    block->next = block;
    block->length = nbytes;
    return (void *)(++block);
}
