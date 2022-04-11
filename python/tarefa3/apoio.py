#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr  5 10:34:58 2022

@author: 70744416353
"""

import pandas as pd
data = pd.read_json('/home/desktop/Documentos/biblia.json')
biblia = pd.read_csv('/home/desktop/Documentos/biblia.csv')

data.info()

import nltk
nltk.download('stopwords')
from nltk.tokenize import RegexpTokenizer

stop_words = nltk.corpus.stopwords.words('portuguese')


words = " ".join(str(x).lower() for x in data["texto"])

tokenizer = RegexpTokenizer(r'[A-z]\w*')
tokens = tokenizer.tokenize(words)
#print(tokens)

tokens_without_sw = [word for word in tokens if not word in stop_words]
freq = nltk.FreqDist(t.lower() for t in tokens_without_sw)

print(freq.most_common()[:150])
