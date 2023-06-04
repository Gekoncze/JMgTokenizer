package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class SeparatorToken extends Token {
    public SeparatorToken() {
    }

    public SeparatorToken(String text, int position) {
        super(text, position);
    }
}
