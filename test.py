import pandas as pd
import numpy as np
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_Score, confusion_matrix
from sklearn.linear_Model import LogisticRegression
from sklearn.neural_network import MLPClassifier
from sklearn.Model_selection import train_test_split
from keras.Models import Sequential
from keras.layers import Dense, Dropout, Activation
from keras.layers import Embedding
from keras.layers import Conv2D, GlobalMaxPooling1D
from keras.preprocessing.text import text_to_word_sequence
import matplotlib.pyplot as plt
from warnings import simplefilter
simplefilter(action='ignore', category=FutureWarning)

stopWords = stopwords.words('english')

# In[61]:


stopWords = stopwords.words('english')

# Question-1.
DF = pd.read_csv('train.csv')
trainX = DF.loc[:, 'sentence'].values
print(trainX[:10])
trainY = DF.loc[:, 'label'].values
print(trainY[:10])

DF = pd.read_csv('test.csv')
testX = DF.loc[:, 'sentence'].values
print(testX[:10])
testY = DF.loc[:, 'label'].values
print(testY[:10])

# Question-2.
plt.hist(trainY)
plt.xlabel('Train Data Labels')
plt.ylabel('Label count')
plt.show()
plt.hist(testY)
plt.xlabel('Test Data Labels')
plt.ylabel('Label count')
plt.show()

# Question-3.
def tokenize(text):
    tokens = word_tokenize(text)
    tokens = [word for word in tokens if not word in stopWords]
    return tokens

vect = TfidfVectorizer(tokenizer=tokenize, use_iDF=True)
train_vector = vect.fit_transform(trainX)
test_vector = vect.transform(testX)


# In[62]:


# Question-4.
Model = MultinomialNB()
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Naive Bayes shows:", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))


# In[63]:


# Question-5.
Model = LogisticRegression(solver='lbfgs')
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Logistic Regression:", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))


# In[64]:


# Question-6.
Model = MLPClassifier(activation='relu', max_iter=500)
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Neural Network ('For Relu'):", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))

Model = MLPClassifier(activation='identity', max_iter=500)
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Neural Network ('FOr Identity'):", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))

Model = MLPClassifier(activation='logistic', max_iter=500)
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Neural Network ('For Logistic'):", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))

Model = MLPClassifier(activation='tanh', max_iter=500)
Model.fit(train_vector, trainY)
predicted = Model.predict(test_vector)
print("Neural Network ('For TanH'):", accuracy_Score(predicted, testY))
print(confusion_matrix(predicted, testY))


# In[65]:


# Question-7.
'''
The Various Results:
For the Naive Bayes Confusion Matrix: [[215  37]
                              [ 72 226]]
For the Naive Bayes Accuracy Score: 0.8018181818181818

For the Logistic Regression Confusion Matrix: [[234  67]
                                      [ 53 196]]
FOr the Logistic Regression Accuracy Score: 0.7818181818181819


Neural Networks: 
FOr iterations = 200.

For the Neural Network Confusion Matrix: [[213  59]
                                 [ 74 204]]
For the Neural Network ('FOr relu') Accuracy Score: 0.7581818181818182

For the Neural Network Confusion Matrix: [[211  64]
                                 [ 76 199]]
For the Neural Network ('FOr identity') Accuracy Score: 0.7454545454545455

For the Neural Network Confusion Matrix: [[216  56]
                                 [ 71 207]]
For the Neural Network ('For logistic') Accuracy Score: 0.769090909090909

For the Neural Network Confusion Matrix: [[210  64]
                                 [ 77 199]]
For the Neural Network ('For tanh') Accuracy Score: 0.7436363636363637


For iterations = 500
For the Neural Network Confusion Matrix: [[213  58]
                                 [ 74 205]]
For the Neural Network ('For relu') Accuracy Score: 0.76

For the Neural Network Confusion Matrix: [[212  64]
                                 [ 75 199]]
For the Neural Network ('For the identity') Accuracy Score: 0.7472727272727273

For the Neural Network Confusion Matrix: [[216  59]
                                 [ 71 204]]
For the Neural Network ('For the logistic') Accuracy Score: 0.7636363636363637

For the Neural Network Confusion Matrix: [[209  63]
                                 [ 78 200]]
For the Neural Network ('For the tanh') Accuracy Score: 0.7436363636363637



Based on this we can see that Naive Bayes gives us the best accuracy. It gives us the best ratio of true values 
against the False values. As you can see the confusion matrix for most of them are similar. But when it comes to Naive Bayes and 
Logistic Regression it is a little different. based on how you want the output, you should decide what method to follow. 

'''


# In[66]:


# Question-8.
Model = Sequential()
Model.add(Dense(1, input_dim=4690))
Model.add(Activation('relu'))
Model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['accuracy'])
Model.fit(train_vector, trainY)
Score = Model.evaluate(test_vector, testY)
print("Keras Sequential Model ('relu'):", Score)

Model = Sequential()
Model.add(Dense(1, input_dim=4690))
Model.add(Activation('softmax'))
Model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['accuracy'])
Model.fit(train_vector, trainY)
Score = Model.evaluate(test_vector, testY)
print("Keras Sequential Model ('softmax'):", Score)


# In[67]:


# Question-9
'''
Two topologies have been tried for the Keras Sequential Model. One with a 'relu' activation function and the other
with a 'softmax' activation function.
The results can be observed from the above output.
'''


# In[68]:


# Question-10
Tokenizer = text.Tokenizer()
DF = pd.read_csv('train.csv')
trainX = DF.loc[:, 'sentence'].values
print(trainX[:10])
trainY = DF.loc[:, 'label'].values
print(trainY[:10])

DF = pd.read_csv('test.csv')
testX = DF.loc[:, 'sentence'].values
print(testX[:10])
testY = DF.loc[:, 'label'].values
print(testY[:10])

def tokenize(text):
    tokens = text_to_word_sequence(text)
    tokens = [word for word in tokens if not word in stopWords]
    return tokens

vect = TfidfVectorizer(tokenizer=tokenize, use_iDF=True)
train_vector = vect.fit_transform(trainX)
test_vector = vect.transform(testX)

Model = Sequential()
Model.add(Dense(1, input_dim=4581))
Model.add(Activation('relu'))
Model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['accuracy'])
Model.fit(train_vector, trainY)
Score = Model.evaluate(test_vector, testY)
print("Keras Sequential Model ('relu') using keras_preprocessing text tokenizer:", Score)

Model = Sequential()
Model.add(Dense(1, input_dim=4581))
Model.add(Activation('softmax'))
Model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['accuracy'])
Model.fit(train_vector, trainY)
Score = Model.evaluate(test_vector, testY)
print("Keras Sequential Model ('softmax') using keras_preprocessing text tokenizer::", Score)


# In[69]:


# Question-11.
# CNN using Keras.
Model = Sequential()
Model.add(Dense(1, input_dim=4690))
Model.add(Conv2D(filters=250, kernel_size=2, padding='valid', activation='relu', strides=1))
Model.add(Activation('sigmoid'))
Model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
Model.fit(train_vector, trainY, validation_data=(test_vector, testY))