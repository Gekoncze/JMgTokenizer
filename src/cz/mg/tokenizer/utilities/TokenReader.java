package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.tokenizer.entities.Token;

public @Utility class TokenReader {
    private final @Mandatory List<Token> tokens;
    private @Optional ListItem<Token> item;

    public TokenReader(@Mandatory List<Token> tokens) {
        this.tokens = tokens;
        this.item = tokens.getFirstItem();
    }

    public void reset() {
        item = tokens.getFirstItem();
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
        validate();
        return move();
    }

    public @Mandatory Token read(@Mandatory Class<? extends Token> type) {
        validate(type);
        return move();
    }

    public @Mandatory Token read(@Mandatory String text) {
        validate(text);
        return move();
    }

    public @Mandatory Token read(@Mandatory TokenPredicate predicate) {
        validate(predicate);
        return move();
    }

    private @Mandatory Token move() {
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        item = item.getNextItem();
        return token;
    }

    public void validate() {
        if (item == null) {
            throw new TokenizeException(tokens.getLast().getPosition(), "Missing token.");
        }
    }

    public void validate(@Mandatory Class<? extends Token> type) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        if (!type.isInstance(token)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token type " + type.getSimpleName() + ", but got " + token.getClass().getSimpleName() + "."
            );
        }
    }

    public void validate(@Mandatory String text) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        if (!token.getText().equals(text)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token '" + text + "', but got '" + token.getText() + "'."
            );
        }
    }

    public void validate(@Mandatory TokenPredicate predicate) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
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
