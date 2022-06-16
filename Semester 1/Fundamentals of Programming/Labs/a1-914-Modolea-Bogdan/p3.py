# Solve the problem from the third set here
# Determine the n-th element of the sequence 1,2,3,2,5,2,3,7,2,3,2,5,...
# obtained from the sequence of natural numbers by replacing composed numbers with their prime divisors,
# without memorizing the elements of the sequence.
from math import sqrt


def is_prime(n):
    if n == 2 or n == 1:
        return True
    if n % 2 == 0:
        return False
    for i in range(3, int(sqrt(n)) + 1, 2):
        if n % i == 0:
            return False
    return True

"""
def divisors(x):
    L = []
    for i in range(2, x // 2 + 1):
        if x % i == 0 and is_prime(i):
            L.append(i)
    return L
"""

def nth_number(n):
    i = 1
    k = n
    while True:
        if not is_prime(i):
            """
            lst = []
            lst = divisors(i)
            for j in range(len(lst)):
                k = k - 1
                if k == 0:
                    return lst[j]
            """
            for j in range(2, i // 2 + 1):
                if i % j == 0 and is_prime(j):
                    k = k - 1
                    if k == 0:
                        return j
        else:
            k = k - 1
            if k == 0:
                return i
        i = i + 1


if __name__ == '__main__':
    n = int(input("Enter n: "))
    print(nth_number(n))

    #num: 1  2  3  2  5  2  3  7  2   3   2   5  11   2   3  13   2   7   3   5   2  17   2   3  19   2   5
    #pos: 1  2  3  4  5  6  7  8  9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27