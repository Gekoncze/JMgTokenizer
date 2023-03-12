package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class SingleQuoteToken extends Token {
    public SingleQuoteToken() {
    }

    public SingleQuoteToken(String text, int position) {
        super(text, position);
    }
}
