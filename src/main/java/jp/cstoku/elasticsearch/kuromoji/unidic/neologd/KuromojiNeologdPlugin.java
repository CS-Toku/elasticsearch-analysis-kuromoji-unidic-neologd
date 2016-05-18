package jp.cstoku.elasticsearch.kuromoji.unidic.neologd;

import java.util.Collection;

import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiAnalyzerProvider;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiBaseFormFilterFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiIterationMarkCharFilterFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiKatakanaStemmerFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiPartOfSpeechFilterFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiReadingFormFilterFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.KuromojiTokenizerFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.PosConcatenationFilterFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis.ReloadableKuromojiTokenizerFactory;
import jp.cstoku.elasticsearch.kuromoji.unidic.neologd.indices.analysis.KuromojiIndicesAnalysisModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;

import com.google.common.collect.ImmutableList;

public class KuromojiNeologdPlugin extends Plugin {
    @Override
    public String name() {
        return "analysis-kuromoji-neologd";
    }

    @Override
    public String description() {
        return "Kuromoji with Neologd analysis support";
    }

    @Override
    public Collection<Module> indexModules(Settings indexSettings) {
        return ImmutableList.<Module> of(new KuromojiIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addCharFilter("kuromoji_unidic_neologd_iteration_mark", KuromojiIterationMarkCharFilterFactory.class);
        module.addAnalyzer("kuromoji_unidic_neologd", KuromojiAnalyzerProvider.class);
        module.addTokenizer("kuromoji_unidic_neologd_tokenizer", KuromojiTokenizerFactory.class);
        module.addTokenFilter("kuromoji_unidic_neologd_baseform", KuromojiBaseFormFilterFactory.class);
        module.addTokenFilter("kuromoji_unidic_neologd_part_of_speech", KuromojiPartOfSpeechFilterFactory.class);
        module.addTokenFilter("kuromoji_unidic_neologd_readingform", KuromojiReadingFormFilterFactory.class);
        module.addTokenFilter("kuromoji_unidic_neologd_stemmer", KuromojiKatakanaStemmerFactory.class);

        module.addTokenizer("reloadable_kuromoji_unidic_neologd_tokenizer", ReloadableKuromojiTokenizerFactory.class);
        module.addTokenizer("reloadable_kuromoji_unidic_neologd", ReloadableKuromojiTokenizerFactory.class);

        module.addTokenFilter("kuromoji_unidic_neologd_pos_concat", PosConcatenationFilterFactory.class);
    }
}