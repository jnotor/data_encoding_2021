e = 7

i = 2

modder = 480

while i < 10000:
    if (e * i ) % modder == 1:
        print(i)
        break

    i+=1