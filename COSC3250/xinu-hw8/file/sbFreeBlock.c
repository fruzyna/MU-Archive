/* sbFreeBlock.c - sbFreeBlock */
/* Copyright (C) 2008, Marquette University.  All rights reserved. */
/*                                                                 */
/* Modified by                                                     */
/*                                                                 */
/* and                                                             */
/*                                                                 */
/*                                                                 */

//TA-BOT:MAILTO liam.fruzyna@marquette.edu weihua.liu@marquette.edu

#include <kernel.h>
#include <device.h>
#include <memory.h>
#include <disk.h>
#include <file.h>

/*------------------------------------------------------------------------
 * sbFreeBlock - Add a block back into the free list of disk blocks.
 *------------------------------------------------------------------------
 */
devcall sbFreeBlock(struct superblock *psuper, int block)
{
    // TODO: Add the block back into the filesystem's list of
    //  free blocks.  Use the superblock's locks to guarantee
    //  mutually exclusive access to the free list, and write
    //  the changed free list segment(s) back to disk.
    struct freeblock *freeblk;
    struct dentry *phw;
    int result;
    int diskfd;

    phw = psuper->sb_disk;
    if(phw == NULL)
    {
      return SYSERR;
    }
    diskfd = phw - devtab;
    if(diskfd < 0 || block < 0)
    {
      return SYSERR;
    }


    //wait for Mutex
    wait(psuper->sb_freelock);

    //create the first block if there is none
    if(psuper->sb_freelst == NULL)
    {
      //create a new block
      struct freeblock *newblk = malloc(sizeof(struct freeblock));
      newblk->fr_count = 0;
      newblk->fr_blocknum = 0;
      psuper->sb_freelst = newblk;

      //write now last block
      seek(diskfd, newblk->fr_blocknum);
      write(diskfd, newblk, sizeof(struct freeblock));
    }

    //finds the last free block in the linked list
    for(freeblk = psuper->sb_freelst; freeblk->fr_next != NULL; freeblk = freeblk->fr_next);

    //adds the given block to the linked list
    if(freeblk->fr_count < FREEBLOCKMAX)
    {
    	//nothing I guess
    }
    else
    {
      //create a new block
      struct freeblock *newblk = malloc(sizeof(struct freeblock));
      newblk->fr_count = 0;
      newblk->fr_blocknum = freeblk->fr_blocknum + 1;
      freeblk->fr_next = newblk;

      //write now last block
      seek(diskfd, freeblk->fr_blocknum);
      write(diskfd, freeblk, sizeof(struct freeblock));

      freeblk = freeblk->fr_next;
    }

	  //add block the last block
    freeblk->fr_count++;
    freeblk->fr_free[freeblk->fr_count - 1] = block;

    //write to disk
    seek(diskfd, freeblk->fr_blocknum);
    write(diskfd, freeblk, sizeof(struct freeblock));

    //release mutex and return
    signal(psuper->sb_freelock);

    return OK;
}
