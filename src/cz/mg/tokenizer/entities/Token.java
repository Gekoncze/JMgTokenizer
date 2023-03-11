package cz.mg.tokenizer.entities;

import cz.mg.annotations.classes.Entity;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Value;

public @Entity class Token {
    private String text;
    private int position;

    public Token() {
    }

    public Token(String text, int position) {
        this.text = text;
        this.position = position;
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
