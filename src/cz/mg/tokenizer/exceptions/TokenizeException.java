package cz.mg.tokenizer.exceptions;

import cz.mg.annotations.classes.Error;

public @Error class TokenizeException extends CodeException {
    public TokenizeException(int position, String message) {
        super(position, message);
    }

    public TokenizeException(int position, String message, Throwable cause) {
        super(position, message, cause);
    }
}
