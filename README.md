Elasticsearch Analysis Kuromoji UniDic Neologd
=======================

## Overview

Elasticsearch Analysis UniDic-Neologd Plugin provides Tokenizer/CharFilter/TokenFilter for Kuromoji with UniDic and Neologd.

## Version

| Version   | Tested on ES | neologd  |
|:---------:|:------------:|:--------:|
| master    | 2.3.X        |          |
| 2.3.2.0   | 2.3.2        | 20160512 |

## Installation

    $ $ES_HOME/bin/plugin install com.github.cstoku/elasticsearch-analysis-kuromoji-unidic-neologd/2.3.2.0

## References

### Analyzer, Tokenizer, TokenFilter, CharFilter

The plugin includes these analyzer and tokenizer, tokenfilter.

| name                                             | type        |
|:-------------------------------------------------|:-----------:|
| kuromoji\_unidic\_neologd\_iteration\_mark       | charfilter  |
| kuromoji\_unidic\_neologd                        | analyzer    |
| kuromoji\_unidic\_neologd\_tokenizer             | tokenizer   |
| kuromoji\_unidic\_neologd\_baseform              | tokenfilter |
| kuromoji\_unidic\_neologd\_part\_of\_speech      | tokenfilter |
| kuromoji\_unidic\_neologd\_readingform           | tokenfilter |
| kuromoji\_unidic\_neologd\_stemmer               | tokenfilter |
| reloadable\_kuromoji\_unidic\_neologd\_tokenizer | tokenizer   |

### Usage

See [Elasticsearch Kuromoji](https://github.com/elastic/elasticsearch-analysis-kuromoji "elasticsearch-analysis-kuromoji").

### What is NEologd

See [mecab-unidic-NEologd](https://github.com/neologd/mecab-unidic-neologd "mecab-unidic-NEologd").

### References to build Lucene Kuromoji with NEologd

* http://d.hatena.ne.jp/Kazuhira/20150316/1426520209
* http://mocobeta-backup.tumblr.com/post/114318023832

