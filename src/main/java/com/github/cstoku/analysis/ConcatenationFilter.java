//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.cstoku.analysis;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;

public abstract class ConcatenationFilter extends TokenFilter {
    protected final CharTermAttribute termAtt = (CharTermAttribute)this.addAttribute(CharTermAttribute.class);
    protected final OffsetAttribute offsetAtt = (OffsetAttribute)this.addAttribute(OffsetAttribute.class);
    protected State current;

    protected ConcatenationFilter(TokenStream input) {
        super(input);
    }

    protected abstract boolean isTarget();

    protected abstract boolean isConcatenated();

    public final boolean incrementToken() throws IOException {
        if(this.current != null) {
            this.restoreState(this.current);
            this.current = null;
            return this.processToken();
        } else {
            return !this.input.incrementToken()?false:this.processToken();
        }
    }

    protected boolean processToken() throws IOException {
        if(!this.isTarget()) {
            return true;
        } else {
            State previousState = this.captureState();
            if(this.input.incrementToken()) {
                if(this.isConcatenated()) {
                    this.concatenateTerms(previousState);
                    return this.processToken();
                }

                this.current = this.captureState();
                this.restoreState(previousState);
            } else {
                this.restoreState(previousState);
            }

            return true;
        }
    }

    protected void concatenateTerms(State previousState) {
        String term = this.termAtt.toString();
        int endOffset = this.offsetAtt.endOffset();
        this.restoreState(previousState);
        this.termAtt.append(term);
        this.offsetAtt.setOffset(this.offsetAtt.startOffset(), endOffset);
    }
}

