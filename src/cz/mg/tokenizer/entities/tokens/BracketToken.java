package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class BracketToken extends Token {
    public BracketToken() {
    }

    public BracketToken(String text, int position) {
        super(text, position);
    }
}
