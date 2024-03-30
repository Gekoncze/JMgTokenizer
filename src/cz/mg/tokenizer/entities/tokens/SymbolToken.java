package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class SymbolToken extends Token {
    public SymbolToken() {
    }

    public SymbolToken(String text, int position) {
        super(text, position);
    }
}
