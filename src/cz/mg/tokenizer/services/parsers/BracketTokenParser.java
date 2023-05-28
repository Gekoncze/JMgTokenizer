package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.BracketToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;

public @Service class BracketTokenParser implements TokenParser {
    private static volatile @Service BracketTokenParser instance;

    public static @Service BracketTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new BracketTokenParser();
                }
            }
        }
        return instance;
    }

    private final boolean[] BRACKETS = new boolean[128];

    private BracketTokenParser() {
        BRACKETS['('] = true;
        BRACKETS[')'] = true;
        BRACKETS['['] = true;
        BRACKETS[']'] = true;
        BRACKETS['{'] = true;
        BRACKETS['}'] = true;
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::bracket)) {
            return TokenBuilder.next(reader).build(BracketToken::new);
        } else {
            return null;
        }
    }

    private boolean bracket(char ch) {
        return ch < BRACKETS.length && BRACKETS[ch];
    }
}
