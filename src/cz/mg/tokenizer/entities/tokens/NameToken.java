package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class NameToken extends Token {
    public NameToken() {
    }

    public NameToken(String text, int position) {
        super(text, position);
    }
}
