#include <stdio.h>
#include <stdlib.h>
/*
	Currency Converter by Liam Fruzyna
	COSC 3250 Assignment 1
*/

int main()
{
    //init variables, start program
    printf("=== Currency Converter ===\n");
    char in = ' ';
    int value = 0;
	int sum = 0;
    char type = ' ';
    //run loop until
    while((in = getchar()) != EOF)
    {
        //if enter is hit start calculating
        if(in == '\n')
        {
            int orig = value;
            //given scales are in 1000s
            value *= 1000;
            //calculate by currency
            if(type == 'E')
            {
                value /= 936;
                printf("€");
            }
            else if(type == 'P')
            {
                value /= 813;
                printf("£");
            }
            else if(type == 'Y')
            {
                value /= 113415;
                printf("¥");
            }
            else if(type == 'R')
            {
                value /= 68163;
                printf("₹");
            }
            else if(type == '$')
            {
                value /= 1000;
                printf("$");
            }
            //printout result
            printf("%d = $%d\n", orig, value);
		sum += value;
            value = 0;
            type = ' ';
        }
        else
        {
            //if no type has been declared
            if(type == ' ')
            {
                //interprit type
                if(in == '$')
                {
                    type = '$';
                }
                else if(in == 0xffffffc2)
                {
                    char next = getchar();
                    if(next == 0xffffffa3)
                    {
                        type = 'P';
                    }
                    else if(next == 0xffffffa5)
                    {
                        type = 'Y';
                    }
                }
                else if(in == 0xffffffe2)
                {
                    char next = getchar();
                    if(next == 0xffffff82)
                    {
                        char fin = getchar();
                        if(fin == 0xffffffac)
                        {
                            type = 'E';
                        }
                        else if(fin == 0xffffffb9)
                        {
                            type = 'R';
                        }
                    }
                }
                else if(in != ' ' || in != '\t')
                {
                    //assume $ if unknown and not blank
                    type = '$';
                }
            }
            else
            {
                if(in - '0' >= 0 || in - '0' < 10)
                {
                    //if number is given increase value
                    value *= 10;
                    value += (in - '0');
                }
                else
                {
                    //if letter is given assume 0
                    value = 0;
                }
            }
        }
    }
	printf("TOTAL = $%d\n\r", sum);
    return 0;
}

