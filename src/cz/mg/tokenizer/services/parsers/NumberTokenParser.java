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

    private final boolean[] NUMBER = new boolean[128];
    private final boolean[] NUMBER_OR_OTHER = new boolean[128];

    private NumberTokenParser() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            NUMBER_OR_OTHER[ch] = true;
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            NUMBER_OR_OTHER[ch] = true;
        }

        for (char ch = '0'; ch <= '9'; ch++) {
            NUMBER[ch] = true;
            NUMBER_OR_OTHER[ch] = true;
        }

        NUMBER_OR_OTHER['.'] = true;
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
            if (reader.has(this::numberOrOther)) {
                builder.getText().append(reader.read());
            } else {
                break;
            }
        }
        return builder.build(NumberToken::new);
    }

    private boolean number(char ch) {
        return ch < NUMBER.length && NUMBER[ch];
    }

    private boolean numberOrOther(char ch) {
        return ch < NUMBER_OR_OTHER.length && NUMBER_OR_OTHER[ch];
    }
}
