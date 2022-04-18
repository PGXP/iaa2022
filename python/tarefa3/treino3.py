#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr  9 10:16:26 2022

@author: gladson
"""

from sklearn.model_selection import train_test_split
import pandas as pd
data = pd.read_json('/home/desktop/Documentos/bibliaV0L1.json')

import nltk
nltk.download('stopwords')
stop_words = nltk.corpus.stopwords.words('portuguese')

dataSSW = []

for frase in data["texto"]:
    words = ' '.join([palavra for palavra in frase.split() if not palavra.lower() in stop_words])
    dataSSW.append(words)

#X_train, X_test, y_train, y_test = train_test_split(dataSSW, data["livro"], test_size=0.2, random_state=7)


from sklearn.feature_extraction.text import CountVectorizer
count_vect = CountVectorizer()
X_train_counts = count_vect.fit_transform(dataSSW)

from sklearn.feature_extraction.text import TfidfTransformer
tfidf_transformer = TfidfTransformer(use_idf=(False))
X_train_tfidf = tfidf_transformer.fit_transform(X_train_counts)

from sklearn.naive_bayes import MultinomialNB
clf = MultinomialNB().fit(X_train_tfidf, data["capitulo"])

docs_new = ['Sê bendito, Senhor, para sempre', 'Pelos frutos das nossas jornadas']
dataQuest = []

for frase in docs_new:
    words = ' '.join([palavra for palavra in frase.split() if not palavra.lower() in stop_words])
    dataQuest.append(words)
    
X_new_counts = count_vect.transform(dataQuest)
X_new_tfidf = tfidf_transformer.transform(X_new_counts)

predicted = clf.predict(X_new_tfidf)

for doc, category in zip(dataQuest, predicted):
    print('%r => %s' % (doc, data["capitulo"]))
    
    
    
from sklearn import metrics
print(metrics.classification_report(X_train_tfidf, predicted,
                                    dataQuest))
                                            
metrics.confusion_matrix(data["livro"], predicted)
    
from sklearn.pipeline import Pipeline
text_clf = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer(use_idf=False)),
                     ('clf', MultinomialNB()),
])
    
text_clf.fit(X_new_tfidf, dataQuest) 

import numpy as np
docs_test = y_train
predicted = text_clf.predict(docs_test)
np.mean(predicted == y_test)


from sklearn.linear_model import SGDClassifier
text_clf = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer(use_idf=False)),
                     ('clf', SGDClassifier(loss='hinge', penalty='l2',
                                           alpha=1e-3, random_state=42,
                                           max_iter=5, tol=None)),
])
text_clf.fit(X_new_tfidf, dataQuest)  

predicted = text_clf.predict(dataQuest)
np.mean(predicted == data["capitulo"])



from sklearn.model_selection import GridSearchCV
parameters = {'vect__ngram_range': [(1, 1), (1, 2)],
              'tfidf__use_idf': (True, False),
              'clf__alpha': (1e-2, 1e-3),
}

gs_clf = GridSearchCV(text_clf, parameters, n_jobs=-1)
gs_clf = gs_clf.fit(twenty_train.data[:400], twenty_train.target[:400])
twenty_train.target_names[gs_clf.predict(['God is love'])[0]]

gs_clf.best_score_                                  

for param_name in sorted(parameters.keys()):
    print("%s: %r" % (param_name, gs_clf.best_params_[param_name]))

