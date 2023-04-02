package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Position;

public @Service class PositionService {
    private static @Optional PositionService instance;

    public static @Mandatory PositionService getInstance() {
        if (instance == null) {
            instance = new PositionService();
        }
        return instance;
    }

    private PositionService() {
    }

    public @Mandatory Position find(@Mandatory String content, int position) {
        int row = 1;
        int column = 1;
        for (int i = 0; i <= position; i++) {
            char ch = content.charAt(i);
            if (ch == '\n') {
                row++;
                column = 1;
            } else {
                column++;
            }
        }
        column--;
        return new Position(row, column);
    }
}
