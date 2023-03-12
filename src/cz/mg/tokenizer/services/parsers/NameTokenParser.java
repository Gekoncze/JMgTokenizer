package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.NameToken;
import cz.mg.tokenizer.utilities.TokenBuilder;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;

public @Service class NameTokenParser implements TokenParser {
    private static @Optional NameTokenParser instance;

    public static @Mandatory NameTokenParser getInstance() {
        if (instance == null) {
            instance = new NameTokenParser();
        }
        return instance;
    }

    private NameTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::uppercase) || reader.has(this::lowercase) || reader.has(this::underscore)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.has()) {
            if (reader.has(this::uppercase) || reader.has(this::lowercase) || reader.has(this::underscore) || reader.has(this::number)) {
                builder.getText().append(reader.next());
            } else {
                break;
            }
        }
        return builder.build(NameToken::new);
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

    private boolean underscore(char ch) {
        return ch == '_';
    }
}
