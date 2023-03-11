package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class CommentToken extends Token {
    public CommentToken() {
    }

    public CommentToken(String text, int position) {
        super(text, position);
    }
}
