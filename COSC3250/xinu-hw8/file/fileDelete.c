/* fileDelete.c - fileDelete */
/* Copyright (C) 2008, Marquette University.  All rights reserved. */
/*                                                                 */
/* Modified by                                                     */
/*                                                                 */
/* and                                                             */
/*                                                                 */
/*                                                                 */

#include <kernel.h>
#include <memory.h>
#include <file.h>

/*------------------------------------------------------------------------
 * fileDelete - Delete a file.
 *------------------------------------------------------------------------
 */
devcall fileDelete(int fd)
{
    // TODO: Unlink this file from the master directory index,
    //  and return its space to the free disk block list.
    //  Use the superblock's locks to guarantee mutually exclusive
    //  access to the directory index.

    //set the block free
    filetab[fd].fn_state = FILE_FREE;

    //write to disk
    wait(supertab->sb_dirlock);
    seek(DISK0, filetab[fd].fn_blocknum);
    //write block
    write(DISK0, filetab[fd].fn_data, DISKBLOCKLEN);
    //write directory block
    seek(DISK0, supertab->sb_dirlst->db_blocknum);
    write(DISK0, supertab->sb_dirlst, sizeof(struct dirblock));
    signal(supertab->sb_dirlock);

    //free the block
    sbFreeBlock(supertab, filetab[fd].fn_blocknum);

    return OK;
}
