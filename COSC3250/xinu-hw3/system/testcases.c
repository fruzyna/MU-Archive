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

devcall putc(int dev, char c) { return 0; }


/**
 * testcases - called after initialization completes to test things.
 */
void testcases(void)
{
    int c;

    kprintf("===TEST BEGIN===\r\n");

    c = kgetc();
    switch (c)
    {
    // TODO: Test your operating system!
		//test case to check for consistant letters
		/*case 'a':
			kprintf("That's the first letter of the alphabet");
			break;*/
    	default:
			kprintf("Hello Xinu World!\r\n");
    }
	/* HW3 Tests
	//test case to print whats given
	kprintf("Type whatever you desire, it will be echoed... I hope (Q to quit)\r\n");
	while((c = kgetc()) != 'Q')
	{
		kprintf("%c", c);
	}

	//test case to check kungetc and kcheckc
	char testString[] = "Hello WORLD";
	kprintf("\r\n\nAttempting to place \"%s\" in ungetBuffer, only 1st 10 chars should be placed\r\n1234567890\r\n", testString);
	//adds string to ungetbuffer
	for(int i = 0; i < sizeof(testString) / sizeof(char); i++)
	{
		kungetc(testString[i]);
	}
	//prints out what is in ungetbuffer
	while(kcheckc())
	{
		kprintf("%c", kgetc());
	}*/

    kprintf("\r\n===TEST END===\r\n");
    return;
}
