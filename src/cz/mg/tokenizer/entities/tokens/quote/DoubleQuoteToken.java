package cz.mg.tokenizer.entities.tokens.quote;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.tokens.QuoteToken;

public @Entity class DoubleQuoteToken extends QuoteToken {
    public DoubleQuoteToken() {
    }

    public DoubleQuoteToken(String text, int position) {
        super(text, position);
    }
}
