/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.cstoku.elasticsearch.kuromoji.unidic.neologd.indices.analysis;

import com.github.cstoku.neologd.unidic.lucene.analysis.ja.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

import java.io.Reader;

/**
 * Registers indices level analysis components so, if not explicitly configured,
 * will be shared among all indices.
 */
public class KuromojiIndicesAnalysis extends AbstractComponent {

    @Inject
    public KuromojiIndicesAnalysis(Settings settings,
                                   IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        indicesAnalysisService.analyzerProviderFactories().put("kuromoji_unidic_neologd",
                new PreBuiltAnalyzerProviderFactory("kuromoji_unidic_neologd", AnalyzerScope.INDICES,
                        new JapaneseAnalyzer()));

        indicesAnalysisService.charFilterFactories().put("kuromoji_unidic_neologd_iteration_mark",
                new PreBuiltCharFilterFactoryFactory(new CharFilterFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_iteration_mark";
                    }

                    @Override
                    public Reader create(Reader reader) {
                        return new JapaneseIterationMarkCharFilter(reader,
                                JapaneseIterationMarkCharFilter.NORMALIZE_KANJI_DEFAULT,
                                JapaneseIterationMarkCharFilter.NORMALIZE_KANA_DEFAULT);
                    }
                }));

        indicesAnalysisService.tokenizerFactories().put("kuromoji_unidic_neologd_tokenizer",
                new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_tokenizer";
                    }

                    @Override
                    public Tokenizer create() {
                        return new JapaneseTokenizer(null, true, JapaneseTokenizer.Mode.SEARCH);
                    }
                }));

        indicesAnalysisService.tokenFilterFactories().put("kuromoji_unidic_neologd_baseform",
                new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_baseform";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        return new JapaneseBaseFormFilter(tokenStream);
                    }
                }));

        indicesAnalysisService.tokenFilterFactories().put(
                "kuromoji_unidic_neologd_part_of_speech",
                new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_part_of_speech";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        return new JapanesePartOfSpeechStopFilter(tokenStream, JapaneseAnalyzer
                                .getDefaultStopTags());
                    }
                }));

        indicesAnalysisService.tokenFilterFactories().put(
                "kuromoji_unidic_neologd_readingform",
                new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_readingform";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        return new JapaneseReadingFormFilter(tokenStream, true);
                    }
                }));

        indicesAnalysisService.tokenFilterFactories().put("kuromoji_unidic_neologd_stemmer",
                new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "kuromoji_unidic_neologd_stemmer";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        return new JapaneseKatakanaStemFilter(tokenStream);
                    }
                }));
    }
}
