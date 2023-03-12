package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;

public @Utility class CodeException extends RuntimeException {
    private final int position;

    public CodeException(int position, String message) {
        super(message);
        this.position = position;
    }

    public CodeException(int position, String message, Throwable cause) {
        super(message, cause);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
