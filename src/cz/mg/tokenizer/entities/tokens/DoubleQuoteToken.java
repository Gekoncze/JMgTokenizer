package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;

public @Entity class DoubleQuoteToken extends QuoteToken {
    public DoubleQuoteToken() {
    }

    public DoubleQuoteToken(String text, int position) {
        super(text, position);
    }
}
