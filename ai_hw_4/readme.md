Your answers must be entered in Canvas by midnight of the day it is due. If the 
question requires a textual response, you can create a PDF and upload that. The 
PDF might be generated from MS-WORD, LATEX, the image of a hand written response,
or using any other mechanism. Code must be uploaded and may require demonstration 
to the TA. Numbers in the parentheses indicate points allocated to the question.

1.In this assignment, you are going to implement probabilistic inference to 
categorize an email(or sms) as spam or not-spam. Typically, spam contains words 
such as -win,cash,inheritance,free and so on. Often spam also contains 
words that are spelled incorrectly. However, you are going to implement as 
implied spam detector -one that uses the occurrence of certain words to classify
the email(or sms) as spam or not-spam. Ideally, one is looking at many words jointly
occurring in a single email (or sms) toc ondently label it as spam. However, the 
true joint distribution faces the curse of dimensionality so you may use NaiveBayes
and assume that the joint distribution can be approximated as the product of the 
distribution of the individual words.

To help us understand the characteristics of spam, you will use the data set available 
at https://archive.ics.uci.edu/ml/machine- learning- databases/00228/ which has 
examples of spam and not-spam. 
First, reserve about 20% of the data for testing (assessing the performance of 
your inference engine). Use the remaining 80% of the data to construct the 
inference engine.

Write a program that uses this 80% of the data to construct your inference engine.
Once constructed, use the remainder of the data to assess the performance of 
your inference engine i.e. predict whether it is ham or spam and compare it with 
the given classification. 

Report your accuracy.You must also upload your entire code.(100p oints)

Hint: Construct a table in which each column is a unique word and each row is an SMS.
A 1 in a location implies that word is present in the SMS, a 0 implies it is not.

You can then estimate the probabilities 
    e.g. P(“win′′|spam) i.e. the probability of the word "win" occurring in a spam
    SMS.

Once you estimate the probabilities, you can simply apply NaiveBayes to label a
new unseen sms as spam or not spam.



TODO: need to recalc probability