# Solve the problem from the first set here

# Given natural number n, determine the prime numbers p1 and p2 such that n = p1 + p2
from math import sqrt

def is_prime(n):
    if n == 2:
        return True
    if n % 2 == 0 or n < 2:
        return False
    for i in range(3, int(sqrt(n)) + 1, 2):
        if n % i == 0:
            return False
    return True

def sum_of_prime_numbers(n):
    ok = False
    if is_prime(n - 2):
        return 2
    for i in range(3, n // 2 + 1, 2):
        if is_prime(i):
            diff = n - i
            if is_prime(diff):
                  return i


if __name__ == '__main__':
    n = int(input("Enter n: "))
    m = int(sum_of_prime_numbers(n))
    print("n = " + str(m) + " + " + str(n - m))


