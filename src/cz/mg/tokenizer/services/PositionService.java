package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;

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

    /**
     * Finds position of given character in string.
     * Returns row and column inside array in this order.
     */
    public int[] find(@Mandatory String content, int position) {
        int row = 1;
        int column = 1;
        char pch = 0;
        for (int i = 0; i <= position; i++) {
            char ch = content.charAt(i);
            if ((ch == '\n' && pch != '\r') || (ch == '\r' && pch != '\n')) {
                row++;
                column = 1;
            } else {
                column++;
            }
            pch = ch;
        }
        column--;
        return new int[]{ row, column };
    }
}
