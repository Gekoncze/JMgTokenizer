package cz.mg.tokenizer.exceptions;

import cz.mg.annotations.classes.Error;

public @Error class TraceableException extends RuntimeException {
    private int position;

    public TraceableException(int position, String message) {
        super(message);
        this.position = position;
    }

    public TraceableException(int position, String message, Throwable cause) {
        super(message, cause);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
