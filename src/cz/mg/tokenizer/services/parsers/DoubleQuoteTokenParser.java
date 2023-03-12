package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.DoubleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;
import cz.mg.tokenizer.utilities.TokenizeException;

public @Service class DoubleQuoteTokenParser implements TokenParser {
    private static @Optional DoubleQuoteTokenParser instance;

    public static @Mandatory DoubleQuoteTokenParser getInstance() {
        if (instance == null) {
            instance = new DoubleQuoteTokenParser();
        }
        return instance;
    }

    private DoubleQuoteTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::doubleQuote)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.next();
        while (reader.has()) {
            if (reader.has(this::doubleQuote) && !reader.hasPrevious(this::backslash)) {
                reader.next();
                return builder.build(DoubleQuoteToken::new);
            } else {
                builder.getText().append(reader.next());
            }
        }
        throw new TokenizeException(builder.getPosition(), "Unclosed double quotes.");
    }

    private boolean doubleQuote(char ch) {
        return ch == '"';
    }

    private boolean backslash(char ch) {
        return ch == '\\';
    }
}
