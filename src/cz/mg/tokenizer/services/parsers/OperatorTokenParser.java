package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.OperatorToken;
import cz.mg.tokenizer.services.TokenParser;

public @Service class OperatorTokenParser implements TokenParser {
    private static volatile @Service OperatorTokenParser instance;

    public static @Service OperatorTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new OperatorTokenParser();
                }
            }
        }
        return instance;
    }

    private final boolean[] OPERATOR = new boolean[128];

    private OperatorTokenParser() {
        OPERATOR['+'] = true;
        OPERATOR['-'] = true;
        OPERATOR['*'] = true;
        OPERATOR['/'] = true;
        OPERATOR['%'] = true;
        OPERATOR['^'] = true;
        OPERATOR['<'] = true;
        OPERATOR['>'] = true;
        OPERATOR['='] = true;
        OPERATOR['~'] = true;
        OPERATOR['!'] = true;
        OPERATOR['&'] = true;
        OPERATOR['|'] = true;
        OPERATOR['?'] = true;
        OPERATOR[':'] = true;
        OPERATOR['.'] = true;
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::operator)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.has()) {
            if (reader.has(this::operator)) {
                builder.getText().append(reader.read());
            } else {
                break;
            }
        }
        return builder.build(OperatorToken::new);
    }

    private boolean operator(char ch) {
        return ch < OPERATOR.length && OPERATOR[ch];
    }
}
