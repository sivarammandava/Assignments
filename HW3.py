import os
import re
nltk.download()
import nltk
from nltk.util import ngrams
import pickle
import time



cwd = os.getcwd()
directory_path = os.path.join(cwd, 'CS 4395')


def main():
    for file in os.listdir(directory_path):
        if file.__contains__('LangId.train.'):
            print('Scanning ', file, '........')
            unigram, bigram = language_model(file)
            with open(file + '.unigram_pickle', 'wb') as handle:
                store_pickle(unigram, handle)
                handle.close()
            with open(file + '.bigram_pickle', 'wb') as handle:
                store_pickle(bigram, handle)
                handle.close()
            print('Finished', file, '.........')
    print('Finished scanning all files')
    print('Initiating test')


def language_model(filename):
    unigram_dict = {}
    bigram_dict = {}
    file_content = get_file_content(filename)
    text = re.sub(r'[.?!,:;()\-\n\d]', ' ', file_content.lower())
    unigrams = nltk.tokenize.word_tokenize(text)
    bigrams = list(ngrams(unigrams, 2))
    for unigram in unigrams:
        if unigram_dict.get(unigram) is None:
            unigram_dict.update({unigram: 1})
        else:
            unigram_dict[unigram] += 1
    for bigram in bigrams:
        if bigram_dict.get(bigram) is None:
            bigram_dict.update({bigram: 1})
        else:
            bigram_dict[bigram] += 1
    # unigram_dict = {t: unigrams.count(t) for t in set(unigrams)}
    # bigram_dict = {b: bigrams.count(b) for b in set(bigrams)}
    return unigram_dict, bigram_dict


def get_file_content(filename):
    file_path = os.path.join(directory_path, filename)
    file = open(file_path, 'r', encoding='utf8')
    file_content = file.read()
    file.close()
    return file_content


def store_pickle(gram, handle):
    pickle.dump(gram, handle)


if name == 'main':
    time_start = time.time()
    main()
    time_end = time.time()
    print('Time:', format(time_end - time_start, '.2f'), 'seconds')