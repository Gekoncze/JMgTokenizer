package cz.mg.tokenizer.entities.tokens.quote;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.tokens.QuoteToken;

public @Entity class SingleQuoteToken extends QuoteToken {
    public SingleQuoteToken() {
    }

    public SingleQuoteToken(String text, int position) {
        super(text, position);
    }
}
