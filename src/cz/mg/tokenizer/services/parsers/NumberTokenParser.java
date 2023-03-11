package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class NumberTokenParser implements TokenParser {
    private static @Optional NumberTokenParser instance;

    public static @Mandatory NumberTokenParser getInstance() {
        if (instance == null) {
            instance = new NumberTokenParser();
        }
        return instance;
    }

    private NumberTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        char ch = reader.read();
        if (isNumber(ch)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.hasNext()) {
            char ch = reader.next();
            if (isNumber(ch) || isDot(ch) || isUppercase(ch) || isLowercase(ch)) {
                builder.getText().append(ch);
            } else {
                reader.previous();
                break;
            }
        }
        return builder.build(NumberToken::new);
    }

    private boolean isUppercase(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private boolean isLowercase(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isDot(char ch) {
        return ch == '.';
    }
}
