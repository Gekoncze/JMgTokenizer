package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class DoubleQuoteToken extends Token {
    public DoubleQuoteToken() {
    }

    public DoubleQuoteToken(String text, int position) {
        super(text, position);
    }
}
