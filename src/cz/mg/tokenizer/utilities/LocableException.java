package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;

public @Utility class LocableException extends RuntimeException {
    private final int position;

    public LocableException(int position, String message) {
        super(message);
        this.position = position;
    }

    public LocableException(int position, String message, Throwable cause) {
        super(message, cause);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
