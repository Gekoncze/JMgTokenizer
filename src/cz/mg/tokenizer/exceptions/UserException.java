package cz.mg.tokenizer.exceptions;

import cz.mg.annotations.classes.Error;

public @Error class UserException extends TraceableException {
    public UserException(int position, String message) {
        super(position, message);
    }

    public UserException(int position, String message, Throwable cause) {
        super(position, message, cause);
    }
}
