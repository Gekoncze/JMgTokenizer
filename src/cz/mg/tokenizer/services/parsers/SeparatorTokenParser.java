package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SeparatorToken;
import cz.mg.tokenizer.services.TokenParser;

public @Service class SeparatorTokenParser implements TokenParser {
    private static volatile @Service SeparatorTokenParser instance;

    public static @Service SeparatorTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SeparatorTokenParser();
                }
            }
        }
        return instance;
    }

    private SeparatorTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::special)) {
            return TokenBuilder.next(reader).build(SeparatorToken::new);
        } else {
            return null;
        }
    }

    private boolean special(char ch) {
        return ch == ',' || ch == ';';
    }
}
