/**
 * @file kprintf.c
 */

/* Embedded Xinu, Copyright (C) 2009, 2013.  All rights reserved. */
//

#include <xinu.h>

#define UNGETMAX 10             /* Can un-get at most 10 characters. */

static unsigned char ungetArray[UNGETMAX];

/**
 * Synchronously read a character from a UART.  This blocks until a character is
 * available.  The interrupt handler is not used.
 *
 * @return
 *      The character read from the UART as an <code>unsigned char</code> cast
 *      to an <code>int</code>.
 */
syscall kgetc(void)
{
    volatile struct pl011_uart_csreg *regptr;
    uchar c;
    
    /* Pointer to the UART control and status registers.  */
    regptr = (struct pl011_uart_csreg *)0x20201000;
    
    // First, check the unget buffer for a character.
    //       Otherwise, check UART flags register, and
    //       once the receiver is not empty, get character c.

	//Checks for a characrer in the ungetbuffer, returns if exists
    if(ungetArray[0] != NULL)
    {
        c = ungetArray[0];
		//remove c from buffer
		for(int i = 0; i < UNGETMAX; i++)
		{
			if(i != UNGETMAX - 1)
			{
				ungetArray[i] = ungetArray[i + 1];
			}
			else
			{
				ungetArray[i] = NULL;
			}
		}
    }
    else
    {
		//otherwise waits for one to return on the serial port
        while((regptr->fr & 0x0010) == 0x0010); //Bit 4 Receive FIFO (not) empty (RXFE)
        c = regptr->dr;
    }
    
    return (int) c;
}

/**
 * kcheckc - check to see if a character is available.
 * @return true if a character is available, false otherwise.
 */
syscall kcheckc(void)
{
    volatile struct pl011_uart_csreg *regptr;
    regptr = (struct pl011_uart_csreg *)0x20201000;
    
    // Check the unget buffer and the UART for characters.
	// Checks for a character in ungetbuffer
    if(ungetArray[0] != NULL)
    {
		//returns true if there is an character in first spot
    	return 1;
    }
	//checks for character in serial port
    if((regptr->fr & 0x0010) != 0x0010) //Bit 4 Receive FIFO (not) empty (RXFE)
    {
		//returns true if there is a character waiting
        return 1;
    }
    
	//returns false when no characters available
    return 0;
}

/**
 * kungetc - put a serial character "back" into a local buffer.
 * @param c character to unget.
 * @return c on success, SYSERR on failure.
 */
syscall kungetc(unsigned char c)
{
    // Check for room in unget buffer, put the character in or discard.
    for(int i = 0; i < UNGETMAX; i++)
    {
        if(ungetArray[i] == NULL)
        {
			//if there is a blank space put the character there
            ungetArray[i] = c;
            return c;
        }
    }
    return SYSERR;
}


/**
 * Synchronously write a character to a UART.  This blocks until the character
 * has been written to the hardware.  The interrupt handler is not used.
 *
 * @param c
 *      The character to write.
 *
 * @return
 *      The character written to the UART as an <code>unsigned char</code> cast
 *      to an <code>int</code>.
 */
syscall kputc(uchar c)
{
    volatile struct pl011_uart_csreg *regptr;
    
    /* Pointer to the UART control and status registers.  */
    regptr = (struct pl011_uart_csreg *)0x20201000;
    
    // Check UART flags register.
    //       Once the Transmitter FIFO is not full, send character c.

	// Waits while transitter fifo is full
    while((regptr->fr & 0x0020 ) == 0x0020);//Transmitter FIFO Full (TXFF) pg 52
    regptr->dr = c;//send character c
    
    return (int)c;
}

/**
 * kernel printf: formatted, synchronous output to SERIAL0.
 *
 * @param format
 *      The format string.  Not all standard format specifiers are supported by
 *      this implementation.  See _doprnt() for a description of supported
 *      conversion specifications.
 * @param ...
 *      Arguments matching those in the format string.
 *
 * @return
 *      The number of characters written.
 */
syscall kprintf(const char *format, ...)
{
    int retval;
    va_list ap;
    
    va_start(ap, format);
    retval = _doprnt(format, ap, (int (*)(int, int))kputc, 0);
    va_end(ap);
    return retval;
    return 0;
}
