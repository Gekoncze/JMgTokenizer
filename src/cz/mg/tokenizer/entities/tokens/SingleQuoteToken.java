package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;

public @Entity class SingleQuoteToken extends QuoteToken {
    public SingleQuoteToken() {
    }

    public SingleQuoteToken(String text, int position) {
        super(text, position);
    }
}
