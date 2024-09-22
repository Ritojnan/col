#include <stdio.h>

int main() {
    int ms, bs, nob, ef, n, mp[100], tif = 0, p = 0;

    printf("Enter the total memory available (in Bytes) -- ");
    scanf("%d", &ms);

    printf("Enter the block size (in Bytes) -- ");
    scanf("%d", &bs);

    nob = ms / bs;
    ef = ms - nob * bs;

    printf("\nEnter the number of processes -- ");
    scanf("%d", &n);

    printf("\nNo. of Blocks available in memory -- %d\n", nob);
    printf("\nPROCESS\tMEMORY REQUIRED\tALLOCATED\tINTERNAL FRAGMENTATION\n");

    for (int i = 0; i < n && p < nob; i++) {
        printf("\n%d\t\t", i + 1);
        scanf("%d", &mp[i]);

        if (mp[i] > bs) {
            printf("\t\tNO\t\t---");
        } else {
            printf("\t\tYES\t%d", bs - mp[i]);
            tif += bs - mp[i];
            p++;
        }
    }

    if (n > p) {
        printf("\nMemory is Full, Remaining Processes cannot be accommodated");
    }

    printf("\nTotal Internal Fragmentation is %d\n", tif);
    printf("Total External Fragmentation is %d\n", ef);

    return 0;
}
