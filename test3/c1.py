import sys
posi = [0, 1, 2, 3, 4, 5,  6,  7,  8,  9]
fibs = [1, 2, 3, 5, 8, 13, 21, 34, 55, 89]
# NOTE: encoding easier to do by hand, since you can just add up fib numbers until
# you get your number

# number_to_encode = 19

number_to_decode = '111100001111001011'

def decode(num):
    fib_sum = 0
    prev = '0'

    for i in range(len(num)):
        char = num[i]

        if char == '1':
            if prev == '1':
                # new num
                continue
            fib_sum += fibs[i]
            

        prev = char

    return fib_sum


def decoder(num):
    # Last element should be dropped
    code_words = num.split('11')[:-1]

    decoded_words = ''
    for word in code_words:
        # have to add in the last 1 again
        decoded_words += ' ' + str(decode(word + '1'))

    return decoded_words

print(decoder(number_to_decode))