import math
import pandas as pd
 
data = pd.read_csv('data.csv')
 
total_games = len(data)

print(data[data['Play']=='Yes'])
print(data[data['Play']=='No'])
outlook_entropy = 0
# get outlook entropy
for val in ['Sunny', 'Overcast', 'Rainy']:
    # get total count of occurrences
    factorProb = len(data[data.Outlook == val])
    # get count of occurrences where play == yes
    yesProb = len(data[(data.Outlook == val) & (data.Play == 'Yes')])
    # get count of occurrences where play == no
    noProb = len(data[(data.Outlook == val) & (data.Play == 'No')])
    
    condition_entropy = factorProb
    if noProb:
        condition_entropy *= -1 * noProb * math.log(noProb, 2) 
    else:
        condition_entropy *= 0

    condition_entropy -= yesProb * math.log(yesProb, 2)
    # print(condition_entropy)

    outlook_entropy += condition_entropy
 
 
# factorProb = 5/14
# noProb = 3/5
# yesProb = 2/5
 
 
# print(outlook_entropy)