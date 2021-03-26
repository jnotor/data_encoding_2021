import pandas as pd
import os, pickle

def remove_stop_words(data):
    with open('terrier_stop_words.txt') as f:
        stops = f.read()

    for word in stops.split('\n'):
        data.replace(word, '')

    return data

def clean_data(data):
    data = data.replace('\t', ' ')
    data = data.replace('(', '').replace(')', '')
    data = data.replace('&', ' ').replace('.', ' ')
    data = data.replace('', '').replace('?', '')
    data = data.replace(';', '').replace(':', '')
    data = data.replace('-', '').replace(':', '')
    data = remove_stop_words(data.lower())

    return data.lower()

def create_prob_table(contents, words):
    # init data frame with all unique words in text as columns
    df = pd.DataFrame(columns=words)

    # traverse each sms as a row/line and make them a new row in db, 
    # setting columns to 1 if word appears
    i = 0
    for line in contents:
        i += 1
        # if not i % 100:
        print(i)
        temp = dict.fromkeys(words, [0])
        
        # NOTE: shouldnt have to clean anymore
        data = clean_data(line)

        spam_state, data = data.split(' ', 1)

        # FIXME: probably have to delete all empty vals in the list... 
        for word in data.split(' '):
            # mark occurrences but also record whether or not the data was ham or spam
            temp[word] = [1] if spam_state == 'spam' else [-1]
        
        temp_df = pd.DataFrame.from_dict(temp)
        df = df.append(temp_df)
    
    # df.to_csv('out.csv')
    return df

def generate_spam_probs(df):
    prob_dict = {}

    for column in df.columns:
        # Get the sum of all spam occurrences; note that ham is a -1 and spam is a 1
        total_spam_occurs = df[column].sum()

        # save probability per word, calculated as occurences / total rows
        prob_dict[column] = total_spam_occurs / len(df)
    
    return prob_dict


def test(contents, p_dict):
    total_spam_detected = 0
    true_total_spam = 0 
    
    count = 0
    for line in contents:
        data = clean_data(line)

        # get the given classification of the line and sep. it from the sms msg
        spam_state, data = data.split(' ', 1)
        
        if spam_state == 'spam':
            true_total_spam += 1

        spam_score = 0
        ham_score = 0
        for word in data.split(' '):
            if word in p_dict:
                # print(word, p_dict[word])
                if p_dict[word] > 0:
                    spam_score += p_dict[word]
                    # print('prob:', p_dict[word], 'current score:', spam_score)
                else:
                    ham_score += abs(p_dict[word])

        if spam_score > ham_score:
            # print('final:', spam_score)
            total_spam_detected += 1
        
        print('spam score:', spam_score, 'ham score:', ham_score)
        if count > 1:
            break
        count += 1
    print(total_spam_detected, true_total_spam)
    return total_spam_detected / true_total_spam
            
            




def main():
    # pickling file so i dont have to reparse this immense data set everytime
    pickle_file = 'pickled_spam.pickle'

    if os.path.isfile(pickle_file):
        with open(pickle_file, 'rb') as pic:
            prob_dict = pickle.load(pic)
    else:
        with open('s/spam_ham.txt') as f:
            set_content = f.read()

        c_data = clean_data(set_content)
        words = set(c_data.replace('\n', '').split(' '))
        
        # FIXME: use d_data to generate
        with open('s/spam_ham.txt') as f:
            contents = f.readlines()
        
        print('here')
        df = create_prob_table(contents, words)
        print('here')

        prob_dict = generate_spam_probs(df)


        with open(pickle_file, 'wb') as pic:
            pickle.dump(prob_dict, pic, protocol=pickle.HIGHEST_PROTOCOL)

    with open('s/testing_spam.txt') as f:
        # FIXME: readlines doesnt remove the new line char
        contents = f.readlines()


    print('detected', test(contents, prob_dict), 'percent of spam')





    

if __name__ == '__main__':
    main()