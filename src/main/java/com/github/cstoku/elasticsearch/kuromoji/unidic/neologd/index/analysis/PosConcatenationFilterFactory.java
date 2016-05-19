
package com.github.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis;

import com.github.cstoku.analysis.ja.PosConcatenationFilter;
import com.github.cstoku.neologd.unidic.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.analysis.Analysis;
import org.elasticsearch.index.settings.IndexSettingsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PosConcatenationFilterFactory extends AbstractTokenFilterFactory {

    private Set<String> posTags = new HashSet<>();

    @Inject
    public PosConcatenationFilterFactory(Index index, IndexSettingsService indexSettingsService, @Assisted String name,
            @Assisted Settings settings, Environment env) {
        super(index, indexSettingsService.getSettings(), name, settings);

        List<String> tagList = Analysis.getWordList(env, settings, "tags");
        if (tagList != null) {
            posTags.addAll(tagList);
        }
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        final PartOfSpeechAttribute posAtt = tokenStream.addAttribute(PartOfSpeechAttribute.class);
        return new PosConcatenationFilter(tokenStream, posTags, new PosConcatenationFilter.PartOfSpeechSupplier() {
            @Override
            public String get() {
                return posAtt.getPartOfSpeech();
            }
        });
    }
}
