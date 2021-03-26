''' https://towardsdatascience.com/spam-classifier-in-python-from-scratch-27a98ddd8e73
'''
import pandas as pd
import os, pickle

def remove_stop_words(data):
    with open('terrier_stop_words.txt') as f:
        stops = f.read()
    s_d = set(stops.split('\n'))

    msg = data.replace('\n', ' \n ').split(' ')
    for i in reversed(range(len(msg))):
        if msg[i] in s_d:
            msg.pop(i)
   
    return data


def clean_data(data):
    data = data.replace('\t', ' ')
    data = data.replace('\'', '').replace('$', '').replace('*', '').replace('`', '')
    data = data.replace('(', '').replace(')', '').replace('%', '').replace('#', '')
    data = data.replace('&', ' ').replace('.', '').replace('\\', '').replace('@', '')
    data = data.replace('', '').replace('?', '').replace('=', '').replace('^', '')
    data = data.replace(';', '').replace(':', '').replace('+', '').replace('_', '')
    data = data.replace('-', '').replace('!', '').replace('�', '').replace('~', '')

    return remove_stop_words(data.lower())


def create_prob_table(contents):
    ''' what we want really is a dict that has words as keys and then its item is
    a dict which contains: 
    {'word_freq_in_spam': num3, 'word_freq_in_ham': num4}
    
    we also need to know:
        total num of spam and ham --> generates P(spam) and P(ham)
        total num of words in spam  --> generates denominator for P(w|spam)
        total num of words in ham  --> generates denominator for P(w|ham)
    ''' 
    # init data frame with all unique words in text as columns
    total_spam = 0
    all_words_in_spam = set([])
    total_ham = 0
    all_words_in_ham = set([])

    unique_words = set(contents.replace('\n', ' ').split(' '))
    main_dict = {}
    for word in unique_words:
        main_dict[word] = {'word_freq_in_spam' : 0, 'word_freq_in_ham':0}

    # the below produces a bug
    # main_dict = dict.fromkeys(unique_words, {'word_freq_in_spam' : 0, 'word_freq_in_ham':0})
    # print(type(main_dict))
    # print(main_dict['eng']['word_freq_in_spam'])
    # print(main_dict['gud']['word_freq_in_spam'])
    # main_dict['eng']['word_freq_in_spam'] = 1 + main_dict['eng']['word_freq_in_spam']
    # print(main_dict['eng']['word_freq_in_spam'])
    # print(main_dict['gud']['word_freq_in_spam'])
    
    # exit()

    # traverse each sms as a row/line and make them a new row in db, 
    for line in contents.split('\n'):
        spam_state, data = line.split(' ', 1)
        
        if spam_state == 'spam':
            total_spam += 1
        else:
            total_ham += 1

        # traverse each word in the sentence
        sentence = list(filter(None, data.split(' ')))
        for word in sentence:
            # keep track of word count in each classification
            if spam_state == 'spam':
                all_words_in_spam.add(word)
                main_dict[word]['word_freq_in_spam'] += 1
            else:              
                all_words_in_ham.add(word)
                main_dict[word]['word_freq_in_ham'] += 1

    totals = {'total_ham' : total_ham, 'total_spam' : total_spam,
              'unique_ham_w_count' : len(all_words_in_ham), 
              'unique_spam_w_count' : len(all_words_in_spam), 
              'all_msg_count' : len(contents)}

    return main_dict, totals


def test(contents, totals, w_freq):
    ''' "For each word w in the processed messaged we find a product of P(w|spam). 
    If w does not exist in the train dataset we take TF(w) as 0 and find P(w|spam)
    using above formula. We multiply this product with P(spam) The resultant 
    product is the P(spam|message). Similarly, we find P(ham|message). Whichever
    probability among these two is greater, the corresponding tag (spam or ham)
    is assigned to the input message. 
    
    NOTE: than we are not dividing by P(w) as given in the formula. This is 
    because both the numbers will be divided by that and it would not affect the
    comparison between the two."
    '''
    total_spam_detected = 0
    true_total_spam = 0 
    for line in contents:
        spam_state, data = line.split(' ', 1)
        
        if spam_state == 'spam':
            true_total_spam += 1

        pHam = 0
        pSpam = 0
       # traverse each word in the sentence
        sentence = list(filter(None, data.split(' ')))
        for word in sentence:
            # NOTE: We're ignoring words in sentences that we've not trained with
            if word in w_freq:
                # calc p(w|spam) and p(w|ham)
                p_w_spam = w_freq[word]['word_freq_in_spam'] / totals['unique_spam_w_count']
                p_w_ham = w_freq[word]['word_freq_in_ham'] / totals['unique_ham_w_count']
                print(p_w_spam, p_w_ham)
                
                # multiply them by P(spam) and P(ham) respectively
                pHam *= p_w_spam * totals['total_spam'] / totals['all_msg_count']
                pSpam *= p_w_ham * totals['total_ham'] / totals['all_msg_count']
        
        if pSpam > pHam:
            # print(pSpam, pHam)
            total_spam_detected += 1

    print(total_spam_detected, true_total_spam)
    return total_spam_detected / true_total_spam
            


def main():

    with open('s/spam_ham.txt') as f:
        set_content = f.read()

    c_data = clean_data(set_content)
    
    word_freqs, totals = create_prob_table(c_data)

    with open('s/testing_spam.txt') as f:
        contents = f.read()

    print('detected', test(contents.replace('\t', ' ').split('\n'), totals, word_freqs) *100 , 'percent of spam')





    

if __name__ == '__main__':
    main()