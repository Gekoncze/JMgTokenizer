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

    private SpecialTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        char ch = reader.read();
        if (isSpecial(ch)) {
            return TokenBuilder.next(reader).build(SpecialToken::new);
        } else {
            return null;
        }
    }

    private boolean isSpecial(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^'
            || ch == '<' || ch == '>' || ch == '=' || ch == '~' || ch == '!' || ch == '&' || ch == '|'
            || ch == '.' || ch == ',' || ch == ';'
            || ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}'
            || ch == '?' || ch == ':'
            || ch == '@' || ch == '$' || ch == '#';
    }
}
