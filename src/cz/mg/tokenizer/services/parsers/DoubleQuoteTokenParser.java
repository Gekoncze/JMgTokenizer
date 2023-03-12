package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.DoubleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

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
        char ch = reader.read();
        if (isDoubleQuote(ch)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.next();
        while (reader.hasNext()) {
            char pch = reader.read();
            char ch = reader.next();
            if (!isBackslash(pch) && isDoubleQuote(ch)) {
                break;
            } else {
                builder.getText().append(ch);
            }
        }
        return builder.build(DoubleQuoteToken::new);
    }

    private boolean isDoubleQuote(char ch) {
        return ch == '"';
    }

    private boolean isBackslash(char ch) {
        return ch == '\\';
    }
}
