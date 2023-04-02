package cz.mg.tokenizer.entities;

import cz.mg.annotations.classes.Entity;
import cz.mg.annotations.requirement.Required;
import cz.mg.annotations.storage.Value;

public @Entity class Position {
    private int row = 1;
    private int column = 1;

    public Position() {
    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Required @Value
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Required @Value
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
