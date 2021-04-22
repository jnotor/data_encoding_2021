import math 

def calc_entropy(in_str):
    char_d = {}
    for char in in_str:
        if char in char_d:
            char_d[char] += 1
        else:
            char_d[char] = 1

    entropy = 0
    for key in char_d:
        probability = char_d[key] / len(in_str)
        l_prob = math.log2(probability)
        entropy += probability * l_prob

    return entropy * -1


print(calc_entropy('EECS'))