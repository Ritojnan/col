#include <stdio.h>

int main() {
    int ms, temp, mp[10], n = 0;
    char ch = 'y';

    printf("Enter the total memory available (in Bytes) -- ");
    scanf("%d", &ms);
    temp = ms;

    do {
        printf("\nEnter memory required for process %d (in Bytes) -- ", n + 1);
        scanf("%d", &mp[n]);

        if (mp[n] <= temp) {
            printf("\nMemory is allocated for Process %d\n", n + 1);
            temp -= mp[n];
            n++;
        } else {
            printf("\nMemory is Full\n");
            break;
        }

        printf("\nDo you want to continue(y/n) -- ");
        scanf(" %c", &ch);
    } while (ch == 'y' || ch == 'Y');

    printf("\n\nTotal Memory Available -- %d\n", ms);
    printf("\n\n\tPROCESS\t\tMEMORY ALLOCATED\n");
    for (int i = 0; i < n; i++) {
        printf("\n \t%d\t\t%d\n", i + 1, mp[i]);
    }
    printf("\n\nTotal Memory Allocated is %d\n", ms - temp);
    printf("Total External Fragmentation is %d\n", temp);

    return 0;
}
