# Solve the problem from the second set here

# Find the smallest number m from the Fibonacci sequence,
# defined by f[0]=f[1]=1, f[n]=f[n-1] + f[n-2],
# for n > 2, larger than the given natural number n.
# (e.g. for n = 6, m = 8).

def next_fibo(n):
    a = b = 1
    while b <= n:
        a, b = b, a + b
    return b

if __name__ == '__main__':
    n = int(input("Enter n: "))
    print(next_fibo(n))