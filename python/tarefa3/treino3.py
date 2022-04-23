#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr  9 10:16:26 2022

@author: gladson
"""

from sklearn.model_selection import train_test_split
import nltk
from nltk.tokenize import RegexpTokenizer
nltk.download('stopwords')
stop_words = nltk.corpus.stopwords.words('portuguese')
tokenizer = RegexpTokenizer(r'[A-z]\w*')

import pandas as pd
data = pd.read_json('/home/desktop/Documentos/biblia.json')

dataSSW = []

for letra in data["texto"]:
    tokens = tokenizer.tokenize(letra)
    tokens_without_sw = [frase for frase in tokens if not str(frase).lower() in stop_words]
    words = " ".join(str(x).lower() for x in tokens_without_sw)
    dataSSW.append(words)

#X_train, X_test, y_train, y_test = train_test_split(dataSSW, data["livro"],test_size=0.2, random_state=66)

from sklearn.feature_extraction.text import CountVectorizer
count_vect = CountVectorizer()
X_train_counts = count_vect.fit_transform(dataSSW)

from sklearn.feature_extraction.text import TfidfTransformer
tf_transformer = TfidfTransformer(use_idf=True).fit(X_train_counts)
X_train_tf = tf_transformer.transform(X_train_counts)

from sklearn.naive_bayes import MultinomialNB
clf = MultinomialNB().fit(X_train_tf, data["livro"])

docs_new = ['O meu combustível pra continuar', 'Jesus é a calmaria, o aconchego dos meus dias',
            'O meu alicerce pra não desistir', 'Não tá sendo fácil aqui, mas eu tenho que seguir',
            'Tem dias que o peito aperta', 'Tem dias que o fardo pesa',
            'Nem que for se arrastando']
X_new_counts = count_vect.transform(docs_new)
# X_new_tfidf = tfidf_transformer.transform(X_new_counts)

predicted = clf.predict(X_new_counts)

for doc, category in zip(docs_new, predicted):
    print('%r => %s' % (doc, category))
    
from sklearn.pipeline import Pipeline
text_clf = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer()),
                     ('clf', MultinomialNB()),
])
    
text_clf.fit(dataSSW, data["livro"]) 

import numpy as np
predicted = text_clf.predict(X_new_counts)
np.mean(predicted == data["livro"])


from sklearn.linear_model import SGDClassifier
text_clf = Pipeline([('vect', CountVectorizer()),
                     ('tfidf', TfidfTransformer(use_idf=False)),
                     ('clf', SGDClassifier(loss='hinge', penalty='l2',
                                           alpha=1e-3, random_state=42,
                                           max_iter=5, tol=None)),
])
text_clf.fit(dataSSW, data["livro"])  

predicted = text_clf.predict(X_new_counts)
np.mean(predicted == data["livro"])

from sklearn import metrics
print(metrics.classification_report(data["livro"], predicted))
                                        
metrics.confusion_matrix(data["livro"], predicted)

from sklearn.model_selection import GridSearchCV
parameters = {'vect__ngram_range': [(1, 1), (1, 2)],
              'tfidf__use_idf': (True, False),
              'clf__alpha': (1e-2, 1e-3),
}

gs_clf = GridSearchCV(text_clf, parameters, n_jobs=-1)
gs_clf = gs_clf.fit(dataSSW, data["livro"])
gs_clf.predict(['Deus é amor'])

gs_clf.best_score_                                  

for param_name in sorted(parameters.keys()):
    print("%s: %r" % (param_name, gs_clf.best_params_[param_name]))

