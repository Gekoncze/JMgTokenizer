package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class QuoteToken extends Token {
    public QuoteToken() {
    }

    public QuoteToken(String text, int position) {
        super(text, position);
    }
}
