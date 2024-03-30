package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SymbolToken;
import cz.mg.tokenizer.services.TokenParser;

public @Service class SymbolsTokenParser implements TokenParser {
    private static volatile @Service SymbolsTokenParser instance;

    public static @Service SymbolsTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SymbolsTokenParser();
                }
            }
        }
        return instance;
    }

    private final boolean[] SYMBOL = new boolean[128];

    private SymbolsTokenParser() {
        SYMBOL['+'] = true;
        SYMBOL['-'] = true;
        SYMBOL['*'] = true;
        SYMBOL['/'] = true;
        SYMBOL['%'] = true;
        SYMBOL['^'] = true;
        SYMBOL['<'] = true;
        SYMBOL['>'] = true;
        SYMBOL['='] = true;
        SYMBOL['~'] = true;
        SYMBOL['!'] = true;
        SYMBOL['&'] = true;
        SYMBOL['|'] = true;
        SYMBOL['?'] = true;
        SYMBOL[':'] = true;
        SYMBOL['.'] = true;
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::symbol)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.has()) {
            if (reader.has(this::symbol)) {
                builder.getText().append(reader.read());
            } else {
                break;
            }
        }
        return builder.build(SymbolToken::new);
    }

    private boolean symbol(char ch) {
        return ch < SYMBOL.length && SYMBOL[ch];
    }
}
