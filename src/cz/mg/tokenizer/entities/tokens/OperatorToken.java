package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class OperatorToken extends Token {
    public OperatorToken() {
    }

    public OperatorToken(String text, int position) {
        super(text, position);
    }
}
