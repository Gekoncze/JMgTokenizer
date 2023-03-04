package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.TokenType;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Utility class TokenReader {
    private final @Mandatory List<Token> tokens;
    private @Optional ListItem<Token> item;

    public TokenReader(@Mandatory List<Token> tokens) {
        this.tokens = tokens;
        this.item = tokens.getFirstItem();
    }

    public boolean hasNext() {
        return item != null;
    }

    public boolean hasNext(@Mandatory TokenType type) {
        return item != null && item.get().getType() == type;
    }

    public boolean hasNext(@Mandatory String text) {
        return item != null && item.get().getText().equals(text);
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

    public @Mandatory Token next(@Mandatory TokenType type) {
        Token token = next();
        validateType(token, type);
        return token;
    }

    public @Mandatory Token next(@Mandatory String text) {
        Token token = next();
        validateText(token, text);
        return token;
    }

    public void validateType(@Mandatory Token token, @Mandatory TokenType type) {
        if (token.getType() != type) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token type " + type.name() + ", but got " + token.getType() + "."
            );
        }
    }

    public void validateText(@Mandatory Token token, @Mandatory String text) {
        if (!token.getText().equals(text)) {
            throw new TokenizeException(
                token.getPosition(),
                "Expected token '" + text + "', but got '" + token.getText() + "'."
            );
        }
    }
}
