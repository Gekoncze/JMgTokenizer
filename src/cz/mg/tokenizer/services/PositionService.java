package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.tokenizer.entities.Position;

public @Service class PositionService {
    private static volatile @Service PositionService instance;

    public static @Service PositionService getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new PositionService();
                }
            }
        }
        return instance;
    }

    private PositionService() {
    }

    public @Mandatory Position find(@Mandatory String content, int position) {
        validate(content, position);
        int row = 1;
        int column = 1;
        for (int i = 0; i <= position; i++) {
            if (i == position) {
                return new Position(row, column);
            } else {
                char ch = content.charAt(i);
                if (ch == '\n') {
                    row++;
                    column = 1;
                } else {
                    column++;
                }
            }
        }
        throw new RuntimeException("Unreachable statement.");
    }

    private void validate(@Mandatory String content, int position) {
        if (position < 0 || position >= content.length()) {
            throw new ArrayIndexOutOfBoundsException(
                "Position " + position + " is out of bounds of string of length " + content.length() + "."
            );
        }
    }
}
