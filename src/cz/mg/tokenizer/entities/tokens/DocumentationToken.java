package cz.mg.tokenizer.entities.tokens;

import cz.mg.annotations.classes.Entity;
import cz.mg.tokenizer.entities.Token;

public @Entity class DocumentationToken extends Token {
    public DocumentationToken() {
    }

    public DocumentationToken(String text, int position) {
        super(text, position);
    }
}
