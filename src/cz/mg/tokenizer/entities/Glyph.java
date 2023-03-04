package cz.mg.tokenizer.entities;

import cz.mg.annotations.classes.Entity;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Value;

public @Entity class Glyph {
    private char character;
    private int position;

    public Glyph() {
    }

    public Glyph(char character, int position) {
        this.character = character;
        this.position = position;
    }

    @Required @Value
    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    @Required @Value
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
