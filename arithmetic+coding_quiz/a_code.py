
def calc_interval(freq, word):
    low = 0
    high = 1

    for char in word:
        Range = high - low
        high = low + Range  * freq[char]['higher']
        low = low + Range * freq[char]['lower']
        print(char, ':', low, high)

    return low, high


def get_char_freq(word):
    freq = {}
    for char in word:
        if char in freq:
            freq[char] += 1
        else:
            freq[char] = 1

    # calculate probability of chars
    for char in freq:
        freq[char] /= len(word)

    i = 0
    freq_intervals = {}
    for char in freq:
        h_bound = l_bound if i > 0 else 1
        l_bound = h_bound - freq[char]
        freq_intervals[char] = {'lower' : l_bound, 'higher' : h_bound}
        i += 1
    
    return freq_intervals


def main():
    code_word = "ohio"

    freq_d = get_char_freq(code_word)

    print(calc_interval(freq_d, code_word))


if __name__ == '__main__':
    main()