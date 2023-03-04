package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;

public @Utility class TokenizeException extends LocableException {
    public TokenizeException(int position, String message) {
        super(position, message);
    }

    public TokenizeException(int position, String message, Throwable cause) {
        super(position, message, cause);
    }
}
