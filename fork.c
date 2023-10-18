#include <stdio.h>
#include <unistd.h>

int main()
{
    char c;

    printf("inserisci un comando\n");
    printf("v per la luce verde\n");
    printf("g per la luce gialla\n");
    printf("r per la luce rossa\n");
    printf("q per uscire\n");
    

    int pid = fork();

    while(1) {
        if(pid == 0)
        {
            printf("\n>>>");
            scanf("%c", &c);
            switch (c) {
                case 'v':
                    for(int i = 0; i<10; i++)
                    {
                        usleep(.3 * 1000000);
                        printf("\nluce verde accesa");
                        usleep(.3 * 1000000);
                        printf("\nluce verde spenta");
                    }
                    continue;
                case 'g':
                    for(int i = 0; i<10; i++)
                    {
                        usleep(.7 * 1000000);
                        printf("\nluce giallo accesa");
                        usleep(.7 * 1000000);
                        printf("\nluce giallo spenta");
                    }
                    continue;
                case 'r':
                    for(int i = 0; i<10; i++)
                    {
                        usleep(1.2 * 1000000);
                        printf("\nluce rosso accesa");
                        usleep(1.2 * 1000000);
                        printf("\nluce rosso spenta");
                    }
                    continue;
                
                default: break;
            }
            break;
        }
    }
    
}