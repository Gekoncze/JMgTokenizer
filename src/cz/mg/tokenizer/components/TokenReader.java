package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.list.ReadableListItem;
import cz.mg.token.Token;
import cz.mg.tokenizer.exceptions.TraceableException;

public @Component class TokenReader {
    private final @Mandatory ReadableList<Token> tokens;
    private final @Mandatory ExceptionFactory exceptionFactory;
    private @Optional ReadableListItem<Token> item;

    public TokenReader(@Mandatory ReadableList<Token> tokens, @Mandatory ExceptionFactory exceptionFactory) {
        this.tokens = tokens;
        this.exceptionFactory = exceptionFactory;
        this.item = tokens.getFirstItem();
    }

    public void reset() {
        item = tokens.getFirstItem();
    }

    public @Optional ReadableListItem<Token> getItem() {
        return item;
    }

    public boolean has() {
        return item != null;
    }

    public boolean hasNext() {
        return item != null
            && item.getNextItem() != null;
    }

    public boolean hasPrevious() {
        return item != null
            && item.getPreviousItem() != null;
    }

    public boolean has(@Mandatory Class<? extends Token> type) {
        return item != null
            && type.isInstance(item.get());
    }

    public boolean hasNext(@Mandatory Class<? extends Token> type) {
        return item != null
            && item.getNextItem() != null
            && type.isInstance(item.getNextItem().get());
    }

    public boolean hasPrevious(@Mandatory Class<? extends Token> type) {
        return item != null
            && item.getPreviousItem() != null
            && type.isInstance(item.getPreviousItem().get());
    }

    public boolean has(@Mandatory String text) {
        return item != null
            && item.get().getText().equals(text);
    }

    public boolean hasNext(@Mandatory String text) {
        return item != null
            && item.getNextItem() != null
            && item.getNextItem().get().getText().equals(text);
    }

    public boolean hasPrevious(@Mandatory String text) {
        return item != null
            && item.getPreviousItem() != null
            && item.getPreviousItem().get().getText().equals(text);
    }

    public boolean has(@Mandatory TokenPredicate predicate) {
        return item != null
            && predicate.match(item.get());
    }

    public boolean hasNext(@Mandatory TokenPredicate predicate) {
        return item != null
            && item.getNextItem() != null
            && predicate.match(item.getNextItem().get());
    }

    public boolean hasPrevious(@Mandatory TokenPredicate predicate) {
        return item != null
            && item.getPreviousItem() != null
            && predicate.match(item.getPreviousItem().get());
    }

    public boolean has(@Mandatory String text, @Mandatory Class<? extends Token> type) {
        return item != null
            && type.isInstance(item.get())
            && item.get().getText().equals(text);
    }

    public boolean hasNext(@Mandatory String text, @Mandatory Class<? extends Token> type) {
        return item != null
            && item.getNextItem() != null
            && type.isInstance(item.getNextItem().get())
            && item.getNextItem().get().getText().equals(text);
    }

    public boolean hasPrevious(@Mandatory String text, @Mandatory Class<? extends Token> type) {
        return item != null
            && item.getPreviousItem() != null
            && type.isInstance(item.getPreviousItem().get())
            && item.getPreviousItem().get().getText().equals(text);
    }

    public @Mandatory Token read() {
        validate();
        return move();
    }

    @SuppressWarnings("unchecked")
    public <T extends Token> @Mandatory T read(@Mandatory Class<T> type) {
        validate(type);
        return (T) move();
    }

    public @Mandatory Token read(@Mandatory String text) {
        validate(text);
        return move();
    }

    public @Mandatory Token read(@Mandatory TokenPredicate predicate) {
        validate(predicate);
        return move();
    }

    @SuppressWarnings("unchecked")
    public <T extends Token> @Mandatory T read(@Mandatory String text, @Mandatory Class<T> type) {
        validate(text);
        validate(type);
        return (T) move();
    }

    public void readEnd() {
        if (item != null) {
            throw exceptionFactory.create(item.get().getPosition(), "Unexpected token.");
        }
    }

    private @Mandatory Token move() {
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        item = item.getNextItem();
        return token;
    }

    public void validate() {
        if (item == null) {
            if (tokens.isEmpty()) {
                throw exceptionFactory.create(-1, "No tokens.");
            } else {
                throw exceptionFactory.create(tokens.getLast().getPosition(), "Missing token.");
            }
        }
    }

    public void validate(@Mandatory Class<? extends Token> type) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        if (!type.isInstance(token)) {
            throw exceptionFactory.create(
                token.getPosition(),
                "Expected token of type " + type.getSimpleName() + ", "
                    + "but got " + token.getClass().getSimpleName() + "."
            );
        }
    }

    public void validate(@Mandatory String text) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        if (!token.getText().equals(text)) {
            throw exceptionFactory.create(
                token.getPosition(),
                "Expected token '" + text + "', "
                    + "but got '" + token.getText() + "'."
            );
        }
    }

    public void validate(@Mandatory TokenPredicate predicate) {
        validate();
        @SuppressWarnings("ConstantConditions")
        Token token = item.get();
        if (!predicate.match(token)) {
            throw exceptionFactory.create(
                token.getPosition(),
                "Expected token matching predicate '" + predicate + "', "
                    + "but got '" + token.getText() + "'."
            );
        }
    }

    public void skip(@Mandatory Class<? extends Token> type) {
        while (has(type)) {
            read();
        }
    }

    public interface TokenPredicate {
        boolean match(@Mandatory Token token);
    }

    public interface ExceptionFactory {
        @Mandatory TraceableException create(int position, @Mandatory String message);
    }
}
