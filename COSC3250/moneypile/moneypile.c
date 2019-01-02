#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

//Liam Fruzyna COSC 3250 Project 2 Moneypile

//main struct for holding inputs
struct moneyblock
{
    unsigned long array[1000];
    int count;
    struct moneyblock *next;
};

//function declarations
void getValues();
void addValue(char, unsigned long);
void addTo(struct moneyblock *, unsigned long);
struct moneyblock * newList();
void finish();
void printOut(struct moneyblock *, char type);

//structs for every currency
struct moneyblock *dollars;
struct moneyblock *pounds;
struct moneyblock *euros;
struct moneyblock *yen;
struct moneyblock *rupes;

int main()
{
    //init lists
    dollars = newList();
    pounds = newList();
    euros = newList();
    yen = newList();
    rupes = newList();
    
    //print header
    printf("\n=== Virtual Money Pile ===\n");
    
    //read input
    getValues();
    
    //print out
    finish();
}

//creates a new moneyblock struct
struct moneyblock * newList()
{
    struct moneyblock *current;
    //allocates the space and sets initial values
    current = (struct moneyblock *)malloc(sizeof(struct moneyblock));
    current->count = 0;
    current->next = NULL;
    return current;
}

//reads in values from console
void getValues()
{
    char in = ' ';
    char type = ' ';
    unsigned long value = 0;
    //breaks on Ctrl D
    while((in = getchar()) != EOF)
    {
        //checks for spaces
        if(isspace(in) && type != ' ')
        {
            //adds values to appropriate struct
            switch(type)
            {
                case '$':
                printf("Putting $%lu in $ pile.\n", value);
                break;
                case 'P':
                printf("Putting £%lu in £ pile.\n", value);
                break;
                case 'E':
                printf("Putting €%lu in € pile.\n", value);
                break;
                case 'R':
                printf("Putting ₹%lu in ₹ pile.\n", value);
                break;
                case 'Y':
                printf("Putting ¥%lu in ¥ pile.\n", value);
                break;
            }
            addValue(type, value);
            type = ' ';
            value = 0;
        }
        else
        {
            if(type == ' ')
            {
                //determines type
                if(in == 0xffffffc2)
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
                else if(in == '$')
                {
                    type = '$';
                }
            }
            else
            {
                //adds new number to value
                value *= 10;
                value += in - '0';
            }
        }
    }
}

//adds value to appropriate struct
void addValue(char type, unsigned long value)
{
    switch(type)
    {
        case '$':
        addTo(dollars, value);
        break;
        case 'P':
        addTo(pounds, value);
        break;
        case 'Y':
        addTo(yen, value);
        break;
        case 'E':
        addTo(euros, value);
        break;
        case 'R':
        addTo(rupes, value);
        break;
    }
}

//adds a value to a given struct
void addTo(struct moneyblock *current, unsigned long value)
{
    if(current->count == 1000)
    {
        //if the array is full move to the next one
        if(current->next == NULL)
        {
            //create a new one if not yet done
            current->next = newList();
        }
        addTo(current->next, value);
    }
    else
    {
        current->array[current->count] = value;
        current->count++;
    }
}

//printout results
void finish()
{
    printf("\n");
    printf("\nVirtual $ pile:\n");
    printOut(dollars, '$');
    printf("\nVirtual £ pile:\n");
    printOut(pounds, 'P');
    printf("\nVirtual ¥ pile:\n");
    printOut(yen, 'Y');
    printf("\nVirtual € pile:\n");
    printOut(euros, 'E');
    printf("\nVirtual ₹ pile:\n");
    printOut(rupes, 'R');
}

//prints out a single structs and childrens values
void printOut(struct moneyblock *current, char type)
{
    //go to next struct if full
    if(current->count == 1000)
    {
        printOut(current->next, type);
    }
    //loop through all values and print
    for(int i = current->count; i > 0; i--)
    {
        switch(type)
        {
            case '$':
            printf("$");
            break;
            case 'P':
            printf("£");
            break;
            case 'E':
            printf("€");
            break;
            case 'R':
            printf("₹");
            break;
            case 'Y':
            printf("¥");
            break;
        }
        current->count--;
        printf("%lu\n", current->array[i - 1]);
    }
    //remove from memory
    free(current);
}
