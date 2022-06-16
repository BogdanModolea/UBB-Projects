#include <stdio.h>

int pwr(int x, int n) {
    /*
     *
     * We compute x^n as:
     *      x * x^(n-1),    if n is odd
     *      (x^2)^(n/2),    if n is even
     *
     * */
    int ans = 1;
    while (n >= 1) {
        if (n % 2) {
            ans *= x;
            n--;
        } else {
            x = x * x;
            n /= 2;
        }
    }
    return ans;
}

void subsequence(int *L, int *maxi, int *save_start, int *save_stop, int a[], int n) {
    /*
     * This functions computes the longest contiguous subsequence
     * such that any two consecutive elements have contrary signs.
     *
     * We check every 2 consecutive elements. If they have contrary signs, then we increment the length of the current
     * subsequence, else we save the maximum length and we save the starting and ending position of the corresponding
     * subsequence.
     *
     * */
    int start;
    start = *save_start = *save_stop = 1;
    for (int i = 2; i <= n; i++) {
        if (a[i] < 0 && a[i - 1] > 0)
            (*L)++;
        else if (a[i] > 0 && a[i - 1] < 0)
            (*L)++;
        else {
            if (*L > *maxi) {
                *maxi = *L;
                *save_start = start;
                *save_stop = i - 1;
            }
            start = i;
            *L = 1;
        }
    }
    if (*L > *maxi) {
        *maxi = *L;
        *save_start = start;
        *save_stop = n;
    }
}

int prime(int x) {
    /*
     * Checking if X is a prime number
     */
    if (x <= 1)
        return 0;
    if (x == 2)
        return 1;
    if (x % 2 == 0)
        return 0;
    for (int i = 3; i * i <= x; i += 2)
        if (x % i == 0)
            return 0;
    return 1;
}

void goldbach(int n, int *first, int *second) {
    /*
     * We check for every prime number if its complement (n - number) is also prime
     */
    if (prime(n - 2))
        *first = 2, *second = n - 2;
    else {
        int x = 3;
        *first = *second = -1;
        while (*first == -1) {
            if (prime(x) && prime(n - x))
                *first = x, *second = n - x;
            x += 2;
        }
    }
}


void menu() {
    printf("\n1. Compute x^n\n"
           "2. Find the longest \n"
           "3. Decompose a given number as sum of 2 prime numbers\n"
           "4. Exit\n");
}


int main() {
    printf("Welcome to my app! :D\n");
    while (1) {
        menu();
        int option;
        printf("Enter a command >");
        scanf("%d", &option);
        switch (option) {
            case 1:
                printf("This is exercise A\n");
                int x, n;
                printf("x=");
                scanf("%d", &x);
                printf("n=");
                scanf("%d", &n);
                printf("x^n=");
                printf("%d\n\n", pwr(x, n));
                break;
            case 2:
                printf("This is exercise B\n");
                int a[101];
                printf("n=");
                scanf("%d", &n);
                for (int i = 1; i <= n; i++) {
                    printf("a[%d]=", i);
                    scanf("%d", &a[i]);
                }

                int L = 1, maxi = 1, save_start, save_stop;
                subsequence(&L, &maxi, &save_start, &save_stop, a, n);


                printf("The longest contiguous subsequence has length ");
                printf("%d\n", maxi);
                for (int i = save_start; i <= save_stop; i++)
                    printf("%d ", a[i]);
                printf("\n\n");
                break;

            case 3:
                printf("n=");
                scanf("%d", &n);
                int first, second;
                goldbach(n, &first, &second);
                printf("%d can be written as %d + %d\n", n, first, second);
                break;

            case 4:
                return 0;

            default:
                printf("You entered a wrong option D:\n");
        }
    }
}


