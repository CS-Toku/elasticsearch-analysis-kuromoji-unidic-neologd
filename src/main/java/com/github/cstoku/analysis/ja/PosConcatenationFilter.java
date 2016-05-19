package com.github.cstoku.analysis.ja;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.github.cstoku.analysis.ConcatenationFilter;
import org.apache.lucene.analysis.TokenStream;

import java.util.Set;

public class PosConcatenationFilter extends ConcatenationFilter {
    private final Set<String> posTags;
    private final PosConcatenationFilter.PartOfSpeechSupplier supplier;

    public PosConcatenationFilter(TokenStream input, Set<String> posTags, PosConcatenationFilter.PartOfSpeechSupplier supplier) {
        super(input);
        this.posTags = posTags;
        this.supplier = supplier;
    }

    protected boolean isTarget() {
        String pos = this.supplier.get();
        return pos != null && this.posTags.contains(pos);
    }

    protected boolean isConcatenated() {
        String pos = this.supplier.get();
        return pos != null && this.posTags.contains(pos);
    }

    public interface PartOfSpeechSupplier {
        String get();
    }
}

