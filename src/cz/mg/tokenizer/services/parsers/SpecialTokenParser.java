package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SpecialToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class SpecialTokenParser implements TokenParser {
    private static @Optional SpecialTokenParser instance;

    public static @Mandatory SpecialTokenParser getInstance() {
        if (instance == null) {
            instance = new SpecialTokenParser();
        }
        return instance;
    }

    private final boolean[] SPECIAL = new boolean[128];

    private SpecialTokenParser() {
        SPECIAL['+'] = true;
        SPECIAL['-'] = true;
        SPECIAL['*'] = true;
        SPECIAL['/'] = true;
        SPECIAL['%'] = true;
        SPECIAL['^'] = true;
        SPECIAL['<'] = true;
        SPECIAL['>'] = true;
        SPECIAL['='] = true;
        SPECIAL['~'] = true;
        SPECIAL['!'] = true;
        SPECIAL['&'] = true;
        SPECIAL['|'] = true;
        SPECIAL['.'] = true;
        SPECIAL[','] = true;
        SPECIAL[';'] = true;
        SPECIAL['?'] = true;
        SPECIAL[':'] = true;
        SPECIAL['@'] = true;
        SPECIAL['$'] = true;
        SPECIAL['#'] = true;
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::special)) {
            return TokenBuilder.next(reader).build(SpecialToken::new);
        } else {
            return null;
        }
    }

    private boolean special(char ch) {
        return ch < SPECIAL.length && SPECIAL[ch];
    }
}
