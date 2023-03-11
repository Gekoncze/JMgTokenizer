package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class StringToken extends Token {
    public StringToken() {
    }

    public StringToken(String text, int position) {
        super(text, position);
    }
}
