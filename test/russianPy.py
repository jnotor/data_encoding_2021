def russianPeasant(a, b):
    result = 0
    iterations = 0
    while b > 0:
        if b & 1:
            result = result + a
        
        # shift 
        a = a << 1;
        b = b >> 1;
        
        # report iterations
        iterations += 1

    print(iterations)

    return result

def main():
    print(russianPeasant(2, 22))

if __name__ == '__main__':
    main()