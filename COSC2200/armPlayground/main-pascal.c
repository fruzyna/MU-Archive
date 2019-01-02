/* COSC 2200 / EECE 2710 Hardware Systems
 * Fall 2016
 *
 * Authors: Liam Fruzyna and Scott Stewart
 * TA-BOT:MAILTO liam.fruzyna@marquette.edu scott.stewart@marquette.edu
 */

/* Functions provided in ARM Playground */
void kprintf(char * , ...);
int getnum(void);
int getchar(void); /* Get character from serial port */

int pascal(int);

int _main_()
{
        int i, n, c;

        n = getnum();

        for (i = 0; i < n; i++)
	{
                for (c = 0; c <= (n - i - 2); c++)
		{
                        kprintf(" ");
		}

                for (c = 0; c <= i; c++)
		{
                        int v = pascal(i) / (pascal(c) * pascal(i - c)) % 2;
                        if (v % 2 == 0)
			{
                                kprintf("  ", v);
                        }
			else
			{
                                kprintf(" *", v);
                        }
                }
                kprintf("\r\n");
        }

        return 0;
}

int pascal(int n)
{
        int c;
        int result = 1;

        for (c = 1; c <= n; c++)
	{
                result = result * c;
	}

        return result;
}
