import pandas as pd


def calculate_probabilites(table):
    probDict = {}
    for word in table:
        hamCount = table[word]['ham']
        spamCount = table[word]['spam']

        prob = spamCount / (hamCount + spamCount)    
        probDict[word] = prob

    return probDict


def get_words(line):
    line = line.replace('.', ' ')
    line = line.replace('!', ' ')
    line = line.replace('?', ' ')
    line = line.replace(',', ' ')
    line = line.split()

    words = []
    for word in line:
        word = word.lower()
        if 'ham' != word and 'spam' != word and word not in words:
            words.append(word)

    return words


def get_all_words(data):
    words = []
    for line in data:
        for word in get_words(line):
            if word not in words:
                words.append(word)

    return words


def make_table(data, allWords):
    table = dict.fromkeys(allWords, {})
    for key in table:
        table[key] = {'spam': 0,
                      'ham':  0}

    for line in data:
        if line[0:4] == 'spam':
            key = 'spam'
        elif line[0:3] == 'ham':
            key = 'ham'

        for word in get_words(line):
            table[word][key] += 1

    return table


def predict(predictData, probDict):
    ''' Function to predict whether unseen messages are spam or ham,
        implementing Naive Bayes.
    '''
    predictions = {}    # line : probability
    for line in predictData:
        prob = 1
        for word in get_words(line):
            if word in probDict:
                prob *= probDict[word]

        if prob > 0:
            predictions[line] = 'spam'
        else:
            predictions[line] = 'ham'
    
    return predictions


def report_accuracy(predictData, predictions):
    rightSpam = 0
    rightHam = 0
    totalSpam = 0
    totalHam = 0
    for line in predictData:
        if line[0:4] == 'spam':
            totalSpam += 1
            if predictions[line] == 'spam':
                rightSpam += 1
        elif line[0:3] == 'ham':
            totalHam += 1
            if predictions[line] == 'ham':
                rightHam += 1

    accuracy = (rightSpam + rightHam) / (totalSpam + totalHam) * 100
    print('Accuracy: ' + str(round(accuracy, 2)) + '%')
    print('\nBreakdown:')
    print('\tNumber of Spam: ' + str(totalSpam))
    print('\tNumber of Correctly Predicted Spam: ' + str(rightSpam))
    print('\tSpam Accuracy: ' + str(round(rightSpam / totalSpam * 100, 2)) + '%')
    print('\n\tNumber of Ham: ' + str(totalHam))
    print('\tNumber of Correctly Predicted Ham: ' + str(rightHam))
    print('\tHam Accuracy: ' + str(round(rightHam / totalHam * 100, 2)) + '%')


f = open('spamData.txt')
data = f.readlines()
f.close()

allWords = get_all_words(data)
table = make_table(data, allWords)

f = open('woo.txt', 'w')
for key in table:
    f.write(key + ' : ' + str(table[key]) + '\n')
f.close()

probs = calculate_probabilites(table)

f = open('testwoo.txt', 'w')
for key in probs:
    f.write(key + ' : ' + str(probs[key]) + '\n')
f.close()



f = open('spamTesting.txt')
predictData = f.readlines()
f.close()

predictions = predict(predictData, probs)
f = open('predictions.txt', 'w')
for key in predictions:
    f.write(key + ' : ' + str(predictions[key]) + '\n')
f.close()

report_accuracy(predictData, predictions)