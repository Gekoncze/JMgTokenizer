package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class WhitespaceToken extends Token {
    public WhitespaceToken() {
    }

    public WhitespaceToken(String text, int position) {
        super(text, position);
    }
}
