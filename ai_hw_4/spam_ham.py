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

    all_words = contents.replace('\n', ' ').split(' ')
    unique_words = set(all_words)
    main_dict = {}
    for word in unique_words:
        main_dict[word] = {'word_freq_in_spam' : 0, 'word_freq_in_ham':0}

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
              'all_word_count' : len(all_words)}

    return main_dict, totals


def handle_line(sentence, totals, w_freq):
    # p_w = 1
    p_w_spam = 1
    p_w_ham = 1

    # traverse each word in the sentence
    for word in sentence:
        # NOTE: We're ignoring words in sentences that we've not trained with
        if word in w_freq:
            # calc p(w|spam) and p(w|ham)
            p_w_ham *= w_freq[word]['word_freq_in_ham'] / totals['unique_ham_w_count']
            p_w_spam *= w_freq[word]['word_freq_in_spam'] / totals['unique_spam_w_count']
            
            # NOTE we dont actually need p_w for comparison since we'll divide both by it
            # p_w *= (w_freq[word]['word_freq_in_ham'] + w_freq[word]['word_freq_in_spam']) / totals['all_word_count']
    
    pHam = p_w_ham * totals['total_ham'] / (totals['total_spam'] + totals['total_ham'])
    pSpam = p_w_spam * totals['total_spam'] / (totals['total_spam'] + totals['total_ham'])

    return pHam, pSpam

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
    accur_spam = accur_ham = t_spam = t_ham = true_t_spam = true_t_ham = 0 
    for line in contents:
        spam_state, data = line.split(' ', 1)
        
        if spam_state == 'spam':
            true_t_spam += 1
        else:
            true_t_ham += 1

        sentence = list(filter(None, data.split(' ')))
        pHam, pSpam = handle_line(sentence, totals, w_freq)
        
        if pSpam >= pHam:
            t_spam += 1
            if spam_state == 'spam':
                accur_spam += 1
        else:
            t_ham += 1
            if spam_state == 'ham':
                accur_ham += 1
    
    return accur_spam, accur_ham, t_spam, t_ham, true_t_spam, true_t_ham
            


def main():

    with open('s/spam_ham.txt') as f:
        set_content = f.read()

    c_data = clean_data(set_content)
    
    word_freqs, totals = create_prob_table(c_data)

    with open('s/full_testing_spam.txt') as f:
        contents = f.read()

    results = test(contents.replace('\t', ' ').split('\n'), totals, word_freqs)

    print('Detected num spam:', results[2])
    print('Detected num ham:', results[3])
    print('True Num spam:', results[4])
    print('True Num ham:', results[5])
    print('Count of accurate spam:', results[0])
    print('Count of accurate ham:', results[1])
    print('Reported number of spam vs. total number of spam:', results[2]/results[4])
    print('Reported number of ham vs. total number of ham:', results[3]/results[5])
    print('Accuracy of spam detection:', results[0]/results[4] * 100)
    print('Accuracy of ham detection:', results[1]/results[5] * 100)
    

if __name__ == '__main__':
    main()