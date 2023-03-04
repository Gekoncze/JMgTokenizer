package cz.mg.tokenizer.entities;

import cz.mg.annotations.classes.Entity;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Value;

public @Entity class Token {
    private TokenType type;
    private String text;
    private int position;

    public Token() {
    }

    public Token(TokenType type, String text, int position) {
        this.type = type;
        this.text = text;
        this.position = position;
    }

    @Required @Value
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Required @Value
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Required @Value
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
