#include<stdio.h>

void swap(int *a, int *b) {
    int c;
    c = *a;
    *a = *b;
    *b = c;
}

int partition(int low, int high, int a[]) {
    int pivot = a[high]; // Choose the last element as pivot
    int j = low;
    for (int i = low; i < high; i++) {
        if (a[i] <= pivot) {
            swap(&a[i], &a[j]);
            j++;
        }
    }
    swap(&a[j], &a[high]);
    return j;
}

void Quicksort(int low, int high, int a[]) {
    if (low < high) {
        int j = partition(low, high, a);
        Quicksort(low, j - 1, a); // Sort elements before partition
        Quicksort(j + 1, high, a); // Sort elements after partition
    }
}

int main() {
    int low, high, i, n;
    printf("Enter the number of elements: ");
    scanf("%d", &n);
    printf("Enter the elements of array: ");
    int a[n];
    for (i = 0; i < n; i++)
        scanf("%d", &a[i]);
    low = 0;
    high = n - 1; // Corrected high value
    Quicksort(low, high, a);
    printf("The sorted array is:\n");
    for (i = 0; i < n; i++)
        printf("%d ", a[i]);
    return 0;
}
