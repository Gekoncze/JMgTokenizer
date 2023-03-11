package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class SpecialToken extends Token {
    public SpecialToken() {
    }

    public SpecialToken(String text, int position) {
        super(text, position);
    }
}
