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

    public void reset() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public boolean has() {
        return position >= 0 && position < content.length();
    }

    public boolean hasPrevious() {
        return (position + 1) >= 0 && (position + 1) < content.length();
    }

    public boolean hasNext() {
        return (position - 1) >= 0 && (position - 1) < content.length();
    }

    public boolean hasAt(int delta) {
        return (position + delta) >= 0 && (position + delta) < content.length();
    }

    public boolean has(char ch) {
        return has() && content.charAt(position) == ch;
    }

    public boolean hasPrevious(char ch) {
        return hasPrevious() && content.charAt(position + 1) == ch;
    }

    public boolean hasNext(char ch) {
        return hasNext() && content.charAt(position - 1) == ch;
    }

    public boolean hasAt(char ch, int delta) {
        return hasNext() && content.charAt(position + delta) == ch;
    }

    public boolean has(@Mandatory CharacterPredicate predicate) {
        return has() && predicate.match(content.charAt(position));
    }

    public boolean hasPrevious(@Mandatory CharacterPredicate predicate) {
        return hasPrevious() && predicate.match(content.charAt(position + 1));
    }

    public boolean hasNext(@Mandatory CharacterPredicate predicate) {
        return hasNext() && predicate.match(content.charAt(position - 1));
    }

    public boolean hasAt(@Mandatory CharacterPredicate predicate, int delta) {
        return hasNext() && predicate.match(content.charAt(position + delta));
    }

    public char read() {
        validate();
        return move();
    }

    public char read(char ch) {
        validate(ch);
        return move();
    }

    public char read(@Mandatory CharacterPredicate predicate) {
        validate(predicate);
        return move();
    }

    private char move() {
        char ch = content.charAt(position);
        position++;
        return ch;
    }

    private void validate() {
        if (!has()) {
            throw new TokenizeException(position, "Missing character.");
        }
    }

    private void validate(char ch) {
        validate();
        char cc = content.charAt(position);
        if (cc != ch) {
            throw new TokenizeException(
                position,
                "Expected character '" + ch + "', but got '" + cc + "'."
            );
        }
    }

    private void validate(@Mandatory CharacterPredicate predicate) {
        validate();
        char ch = content.charAt(position);
        if (!predicate.match(ch)) {
            throw new TokenizeException(
                position,
                "Expected character matching predicate '" + predicate + "', but got '" + ch + "'."
            );
        }
    }

    public interface CharacterPredicate {
        boolean match(char ch);
    }
}
