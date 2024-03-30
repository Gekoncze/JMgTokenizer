package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SymbolToken;
import cz.mg.tokenizer.services.TokenParser;

public @Service class SymbolTokenParser implements TokenParser {
    private static volatile @Service SymbolTokenParser instance;

    public static @Service SymbolTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SymbolTokenParser();
                }
            }
        }
        return instance;
    }

    private final boolean[] SYMBOL = new boolean[128];

    private SymbolTokenParser() {
        SYMBOL['@'] = true;
        SYMBOL['$'] = true;
        SYMBOL['#'] = true;
        SYMBOL[','] = true;
        SYMBOL[';'] = true;
        SYMBOL['('] = true;
        SYMBOL[')'] = true;
        SYMBOL['['] = true;
        SYMBOL[']'] = true;
        SYMBOL['{'] = true;
        SYMBOL['}'] = true;
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
            return TokenBuilder.build(reader, SymbolToken::new);
        } else {
            return null;
        }
    }

    private boolean symbol(char ch) {
        return ch < SYMBOL.length && SYMBOL[ch];
    }
}
