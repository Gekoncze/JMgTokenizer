package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.NameToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class NameTokenParser implements TokenParser {
    private static volatile @Service NameTokenParser instance;

    public static @Service NameTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new NameTokenParser();
                }
            }
        }
        return instance;
    }

    private final boolean[] NAME = new boolean[128];
    private final boolean[] NAME_OR_NUMBER = new boolean[128];

    private NameTokenParser() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            NAME[ch] = true;
            NAME_OR_NUMBER[ch] = true;
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            NAME[ch] = true;
            NAME_OR_NUMBER[ch] = true;
        }

        for (char ch = '0'; ch <= '9'; ch++) {
            NAME_OR_NUMBER[ch] = true;
        }

        NAME['_'] = true;
        NAME_OR_NUMBER['_'] = true;
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::name)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.has()) {
            if (reader.has(this::nameOrNumber)) {
                builder.getText().append(reader.read());
            } else {
                break;
            }
        }
        return builder.build(NameToken::new);
    }

    private boolean name(char ch) {
        return ch < NAME.length && NAME[ch];
    }

    private boolean nameOrNumber(char ch) {
        return ch < NAME_OR_NUMBER.length && NAME_OR_NUMBER[ch];
    }
}
