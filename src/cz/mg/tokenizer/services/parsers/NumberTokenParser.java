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
        if (reader.has(this::number)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.has()) {
            if (reader.has(this::number) || reader.has(this::dot) || reader.has(this::uppercase) || reader.has(this::lowercase)) {
                builder.getText().append(reader.next());
            } else {
                break;
            }
        }
        return builder.build(NumberToken::new);
    }

    private boolean uppercase(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private boolean lowercase(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean number(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean dot(char ch) {
        return ch == '.';
    }
}
