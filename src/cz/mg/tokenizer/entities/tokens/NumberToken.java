package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class NumberToken extends Token {
    public NumberToken() {
    }

    public NumberToken(String text, int position) {
        super(text, position);
    }
}
