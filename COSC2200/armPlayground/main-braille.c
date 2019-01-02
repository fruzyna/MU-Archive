/* COSC 2200 / EECE 2710 Hardware Systems
 * Fall 2016
 * Template for Braille Translator (HW8)
 *
 * Authors: Liam Fruzyna and Scott Stewart
 * TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquette.edu
 */

#define EOF 4

/* Functions provided in ARM Playground */
void kprintf(char *, ...);
int  getchar(void);		/* Get character from serial port */

/* Encoding of Braille characters */
unsigned int brailleTable[] = {32, /* a */
		40, /* b */
		48, /* c */ 
		52, /* d */
		36, /* e */
		56, /* f */
		60, /* g */
		44, /* h */
		24, /* i */
		28, /* j */
		34, /* k */
		42, /* l */
		50, /* m */
		54, /* n */
		38, /* o */
		58, /* p */
		62, /* q */
		46, /* r */
		26, /* s */
		30, /* t */
		35, /* u */
		43, /* v */
		29, /* w */
		51, /* x */
		55, /* y */
		39, /* z */
		28, /* 0 */
		32, /* 1 */
		40, /* 2 */
		48, /* 3 */
		52, /* 4 */
		36, /* 5 */
		56, /* 6 */
		60, /* 7 */
		44, /* 8 */
		24, /* 9 */
		23, /* # */
		1, /* Capital */
		5, /* Letter */
		17, /* Radix */
		13}; /* Period */

#define CAPITAL 37
#define LETTER  38
#define RADIX   39
#define PERIOD	40
#define MAX 26

int _main_(void)
{
	int c = 0;

	kprintf("Braille Translator\r\n");
	while (c != EOF)
	{
		int num = 0;
		char array[MAX];
		int pos = 0;
		for(pos = 0; (c = getchar()) != '\n'; pos++)
		{
			/* Here is a stub that merely echoes the input back. */
			kprintf("%c", c);
			int cap = 0;
			int key = 0;
			if(c >= 97)
			{
				num = 0;
				key = brailleTable[c - 97];
			}
			else if(c >= 65)
			{
				num = 0;
				array[pos] = brailleTable[CAPITAL];
				pos++;
				key = brailleTable[c - 65];
			}
			else if(c >= 48)
			{
				if(num != 1)
				{
					array[pos] = brailleTable[RADIX];
					pos++;
					num = 1;
				}
				key = brailleTable[c - 48];
			}
			else if(c == 46)
			{
				num = 0;
				key = brailleTable[PERIOD];
			}
			else
			{
				num = 0;
				array[pos] = 0;
			}
			array[pos] = key;
		}
		kprintf("\r\n");

		int i = 0;
		for(i = 0; i < pos; i++)
		{		
			if(array[i] >= 32)
			{
				kprintf("@");
				array[i] -= 32;
			}
			else
			{
				kprintf(".");
			}
			if(array[i] >= 16)
			{
				kprintf("@");
				array[i] -= 16;
			}
			else
			{
				kprintf(".");
			}
			kprintf(" ");
		}
		kprintf("\r\n");
		for(i = 0; i < pos; i++)
		{		
			if(array[i] >= 8)
			{
				kprintf("@");
				array[i] -= 8;
			}
			else
			{
				kprintf(".");
			}
			if(array[i] >= 4)
			{
				kprintf("@");
				array[i] -= 4;
			}
			else
			{
				kprintf(".");
			}
			kprintf(" ");
		}
		kprintf("\r\n");
		for(i = 0; i < pos; i++)
		{
			if(array[i] >= 2)
			{
				kprintf("@");
				array[i] -= 2;
			}
			else
			{
				kprintf(".");
			}
			if(array[i] >= 1)
			{
				kprintf("@");
				array[i] -= 1;
			}
			else
			{
				kprintf(".");
			}
			kprintf(" ");
		}
		kprintf("\r\n");
	}
}

