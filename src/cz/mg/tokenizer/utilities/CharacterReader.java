package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;

public @Utility class CharacterReader {
    private final @Mandatory String content;
    private int position;

    public CharacterReader(@Mandatory String content) {
        this.content = content;
        this.position = 0;
    }

    public int getPosition() {
        return position;
    }

    public boolean has() {
        return position >= 0 && position < content.length();
    }

    public boolean hasNext() {
        return (position + 1) >= 0 && (position + 1) < content.length();
    }

    public boolean hasPrevious() {
        return (position - 1) >= 0 && (position - 1) < content.length();
    }

    public boolean has(char ch) {
        return has() && content.charAt(position) == ch;
    }

    public boolean hasNext(char ch) {
        return has() && hasNext() && content.charAt(position + 1) == ch;
    }

    public boolean hasPrevious(char ch) {
        return has() && hasPrevious() && content.charAt(position - 1) == ch;
    }

    public boolean has(@Mandatory CharacterPredicate predicate) {
        return has() && predicate.match(content.charAt(position));
    }

    public boolean hasNext(@Mandatory CharacterPredicate predicate) {
        return has() && hasNext() && predicate.match(content.charAt(position + 1));
    }

    public boolean hasPrevious(@Mandatory CharacterPredicate predicate) {
        return has() && hasPrevious() && predicate.match(content.charAt(position - 1));
    }

    public char read() {
        if (has()) {
            return content.charAt(position);
        } else {
            throw new TokenizeException(position, "Missing character.");
        }
    }

    public char read(char ch) {
        validate(ch);
        return read();
    }

    public char read(@Mandatory CharacterPredicate predicate) {
        validate(predicate);
        return read();
    }

    public char next() {
        if (has()) {
            char ch = content.charAt(position);
            position++;
            return ch;
        } else {
            throw new TokenizeException(position, "Missing character.");
        }
    }

    public char next(char ch) {
        validate(ch);
        return next();
    }

    public char next(@Mandatory CharacterPredicate predicate) {
        validate(predicate);
        return next();
    }

    public char previous() {
        if (has()) {
            char ch = content.charAt(position);
            position--;
            return ch;
        } else {
            throw new TokenizeException(position, "Missing character.");
        }
    }

    public char previous(char ch) {
        validate(ch);
        return previous();
    }

    public char previous(@Mandatory CharacterPredicate predicate) {
        validate(predicate);
        return previous();
    }

    public void validate(char c) {
        char ch = read();
        if (ch != c) {
            throw new TokenizeException(
                position,
                "Expected character '" + c + "', but got '" + ch + "'."
            );
        }
    }

    public void validate(@Mandatory CharacterPredicate predicate) {
        char ch = read();
        if (!predicate.match(ch)) {
            throw new TokenizeException(
                position,
                "Expected character matching predicate '" + predicate + "', but got '" + ch + "'."
            );
        }
    }

    public void reset() {
        position = 0;
    }

    public interface CharacterPredicate {
        boolean match(char ch);
    }
}
