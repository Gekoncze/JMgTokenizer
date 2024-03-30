package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.quote.SingleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;
import cz.mg.tokenizer.exceptions.TokenizeException;

public @Service class SingleQuoteTokenParser implements TokenParser {
    private static volatile @Service SingleQuoteTokenParser instance;

    public static @Service SingleQuoteTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SingleQuoteTokenParser();
                }
            }
        }
        return instance;
    }

    private SingleQuoteTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::singleQuote)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.read();
        while (reader.has()) {
            if (reader.has(this::backslash) && reader.hasNext()) {
                builder.getText().append(reader.read());
                builder.getText().append(reader.read());
            } else if (reader.has(this::singleQuote)) {
                reader.read();
                return builder.build(SingleQuoteToken::new);
            } else {
                builder.getText().append(reader.read());
            }
        }
        throw new TokenizeException(builder.getPosition(), "Unclosed single quotes.");
    }

    private boolean singleQuote(char ch) {
        return ch == '\'';
    }

    private boolean backslash(char ch) {
        return ch == '\\';
    }
}
