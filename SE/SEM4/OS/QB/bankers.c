#include <stdio.h>
#include <stdbool.h>

int main() {
    int noPro, noRes;
    printf("Enter the number of processes: ");
    scanf("%d", &noPro);

    printf("Enter the number of resources: ");
    scanf("%d", &noRes);

    int totalIns[noRes];
    printf("Enter the total instances of resources: ");
    for (int i = 0; i < noRes; i++) {
        printf("\nInstances of resource %d: ", i);
        scanf("%d", &totalIns[i]);
    }

    printf("Enter the max matrix:\n");
    int max[noPro][noRes];
    for (int i = 0; i < noPro; i++)
        for (int j = 0; j < noRes; j++)
            scanf("%d", &max[i][j]);

    printf("Enter the allocation matrix:\n");
    int alloc[noPro][noRes];
    for (int i = 0; i < noPro; i++)
        for (int j = 0; j < noRes; j++)
            scanf("%d", &alloc[i][j]);

    int need[noPro][noRes];
    printf("The need matrix is:\n");
    for (int i = 0; i < noPro; i++) {
        for (int j = 0; j < noRes; j++) {
            need[i][j] = max[i][j] - alloc[i][j];
            printf("%d ", need[i][j]);
        }
        printf("\n");
    }

    int totalAlloc[noRes];
    for (int j = 0; j < noRes; j++) {
        totalAlloc[j] = 0;
        for (int i = 0; i < noPro; i++) {
            totalAlloc[j] += alloc[i][j];
        }
    }

    int avail[noRes], copyAvail[noRes];
    printf("Available: \n");
    for (int i = 0; i < noRes; i++) {
        avail[i] = totalIns[i] - totalAlloc[i];
        printf("%d ", avail[i]);
        copyAvail[i] = avail[i];
    }
    printf("\n");

    bool done[noPro];
    for (int i = 0; i < noPro; i++)
        done[i] = false;

    int count = 1;
    for (int i = 0; i < noPro; i = (i + 1) % noPro) {
        if (count > 2)
            break;

        if (!done[i]) {
            bool flag = false;
            int k;
            for (k = i; k < noPro; k++) {
                for (int j = 0; j < noRes; j++) {
                    if (avail[j] < need[k][j])
                        flag = true;
                }

                if (flag)
                    continue;
                else {
                    if (!done[k]) {
                        printf("P%d: ", k);
                        done[k] = true;
                        for (int l = 0; l < noRes; l++)
                            printf("%d ", avail[l]);
                        printf("+ ");
                        for (int l = 0; l < noRes; l++)
                            printf("%d ", alloc[k][l]);
                        printf("= ");

                        for (int l = 0; l < noRes; l++) {
                            avail[l] += alloc[k][l];
                            printf("%d ", avail[l]);
                        }
                        printf("\n");
                    } else
                        continue;
                }
            }
        }

        if (i == noPro - 1)
            count++;
    }

    int check;
    for (check = 0; check < noPro; check++) {
        if (!done[check]) {
            printf("Not a safe state\n");
            return 0;
        }
    }
    printf("Safe state!\n");

    printf("Enter the process no: ");
    int pno;
    scanf("%d", &pno);
    printf("Enter the new requests:\n");
    for (int i = 0; i < noRes; i++) {
        int x;
        scanf("%d", &x);
        need[pno][i] -= x;
        copyAvail[i] -= x;
        alloc[pno][i] += x;
    }

    printf("The new need matrix is:\n");
    for (int i = 0; i < noPro; i++) {
        for (int j = 0; j < noRes; j++) {
            need[i][j] = max[i][j] - alloc[i][j];
            printf("%d ", need[i][j]);
        }
        printf("\n");
    }

    int done1[noPro];
    for (int i = 0; i < noPro; i++)
        done1[i] = 0;

    int count1 = 1;
    for (int i = 0; i < noPro; i = (i + 1) % noPro) {
        if (count1 > 2)
            break;

        if (done1[i] == 0) {
            bool flag = false;
            int k;
            for (k = i; k < noPro; k++) {
                for (int j = 0; j < noRes; j++) {
                    if (copyAvail[j] < need[k][j])
                        flag = true;
                }

                if (flag)
                    continue;
                else {
                    if (done1[k] == 0) {
                        printf("P%d: ", k);
                        done1[k] = 1;
                        for (int l = 0; l < noRes; l++)
                            printf("%d ", copyAvail[l]);
                        printf("+ ");
                        for (int l = 0; l < noRes; l++)
                            printf("%d ", alloc[k][l]);
                        printf("= ");

                        for (int l = 0; l < noRes; l++) {
                            copyAvail[l] += alloc[k][l];
                            printf("%d ", copyAvail[l]);
                        }
                        printf("\n");
                    } else
                        continue;
                }
            }
        }

        if (i == noPro - 1)
            count1++;
    }

    int check1;
    for (check1 = 0; check1 < noPro; check1++) {
        if (done1[check1] == 0) {
            printf("Deadlock detected hence no process should be added\n");
            return 0;
        }
    }
    printf("Safe state again! \nRequest can be granted\n");

    return 0;
}
