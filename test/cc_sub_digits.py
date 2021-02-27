def sumDigits(n):
    Sum = 0
    while n > 0:
        Sum += n % 10
        n /= 10
        n = int(n)

    print(Sum)

sumDigits(12345)