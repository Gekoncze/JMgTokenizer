package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class CharacterToken extends Token {
    public CharacterToken() {
    }

    public CharacterToken(String text, int position) {
        super(text, position);
    }
}
