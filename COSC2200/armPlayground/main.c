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
	int n = 0;
	while(n != 6969)
	{
		n = getnum();

        int v = n % 2;
	kprintf("%d", v);
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
