package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class WordToken extends Token {
    public WordToken() {
    }

    public WordToken(String text, int position) {
        super(text, position);
    }
}
