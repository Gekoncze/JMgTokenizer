package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SingleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class SingleQuoteTokenParser implements TokenParser {
    private static @Optional SingleQuoteTokenParser instance;

    public static @Mandatory SingleQuoteTokenParser getInstance() {
        if (instance == null) {
            instance = new SingleQuoteTokenParser();
        }
        return instance;
    }

    private SingleQuoteTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        char ch = reader.read();
        if (isSingleQuote(ch)) {
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
            if (!isBackslash(pch) && isSingleQuote(ch)) {
                break;
            } else {
                builder.getText().append(ch);
            }
        }
        return builder.build(SingleQuoteToken::new);
    }

    private boolean isSingleQuote(char ch) {
        return ch == '\'';
    }

    private boolean isBackslash(char ch) {
        return ch == '\\';
    }
}
