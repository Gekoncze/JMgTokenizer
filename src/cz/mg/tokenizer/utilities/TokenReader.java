package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

import java.util.NoSuchElementException;

public @Utility class TokenReader {
    private final @Mandatory List<Token> tokens;
    private @Optional ListItem<Token> item;

    public TokenReader(@Mandatory List<Token> tokens) {
        this.tokens = tokens;
        this.item = tokens.getFirstItem();
    }

    public boolean has() {
        return item != null;
    }

    public boolean hasNext() {
        return item != null && item.getNextItem() != null;
    }

    public boolean hasPrevious() {
        return item != null && item.getPreviousItem() != null;
    }

    public boolean has(@Mandatory Class<? extends Token> type) {
        return item != null && type.isInstance(item.get());
    }

    public boolean hasNext(@Mandatory Class<? extends Token> type) {
        return item != null && item.getNextItem() != null && type.isInstance(item.getNextItem().get());
    }

    public boolean hasPrevious(@Mandatory Class<? extends Token> type) {
        return item != null && item.getPreviousItem() != null && type.isInstance(item.getPreviousItem().get());
    }

    public boolean has(@Mandatory String text) {
        return item != null && item.get().getText().equals(text);
    }

    public boolean hasNext(@Mandatory String text) {
        return item != null && item.getNextItem() != null && item.getNextItem().get().getText().equals(text);
    }

    public boolean hasPrevious(@Mandatory String text) {
        return item != null && item.getPreviousItem() != null && item.getPreviousItem().get().getText().equals(text);
    }

    public boolean has(@Mandatory TokenPredicate predicate) {
        return item != null && has() && predicate.match(item.get());
    }

    public boolean hasNext(@Mandatory TokenPredicate predicate) {
        return item != null && hasNext() && predicate.match(item.getNextItem().get());
    }

    public boolean hasPrevious(@Mandatory TokenPredicate predicate) {
        return item != null && hasPrevious() && predicate.match(item.getPreviousItem().get());
    }

    public @Mandatory Token read() {
        if (item != null) {
            return item.get();
        } else {
            throw new NoSuchElementException("Missing token.");
        }
    }

    public @Mandatory Token read(@Mandatory Class<? extends Token> type) {
        validate(type);
        return read();
    }

    public @Mandatory Token read(@Mandatory String text) {
        validate(text);
        return read();
    }

    public @Mandatory Token read(@Mandatory TokenPredicate predicate) {
        validate(predicate);
        return read();
    }

    public @Mandatory Token next() {
        if (item != null) {
            Token token = item.get();
            item = item.getNextItem();
            return token;
        } else {
            throw new TokenizeException(tokens.getLast().getPosition(), "Missing token.");
        }
    }

    public @Mandatory Token next(@Mandatory Class<? extends Token> type) {
        validate(type);
        return next();
    }

    public @Mandatory Token next(@Mandatory String text) {
        validate(text);
        return next();
    }

    public @Mandatory Token next(@Mandatory TokenPredicate predicate) {
        validate(predicate);
        return next();
    }

    public @Mandatory Token previous() {
        if (item != null) {
            Token token = item.get();
            item = item.getPreviousItem();
            return token;
        } else {
            throw new TokenizeException(tokens.getFirst().getPosition(), "Missing token.");
        }
    }

    public @Mandatory Token previous(@Mandatory Class<? extends Token> type) {
        validate(type);
        return previous();
    }

    public @Mandatory Token previous(@Mandatory String text) {
        validate(text);
        return previous();
    }

    public @Mandatory Token previous(@Mandatory TokenPredicate predicate) {
        validate(predicate);
        return previous();
    }

    public void validate(@Mandatory Class<? extends Token> type) {
        Token token = read();
        if (!type.isInstance(token)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token type " + type.getSimpleName() + ", but got " + token.getClass().getSimpleName() + "."
            );
        }
    }

    public void validate(@Mandatory String text) {
        Token token = read();
        if (!token.getText().equals(text)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token '" + text + "', but got '" + token.getText() + "'."
            );
        }
    }

    public void validate(@Mandatory TokenPredicate predicate) {
        Token token = read();
        if (!predicate.match(token)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token matching predicate '" + predicate + "', but got '" + token.getText() + "'."
            );
        }
    }

    public interface TokenPredicate {
        boolean match(@Mandatory Token token);
    }
}
