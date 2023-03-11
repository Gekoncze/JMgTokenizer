package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.WhitespaceToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class WhitespaceTokenParser implements TokenParser {
    private static @Optional WhitespaceTokenParser instance;

    public static @Mandatory WhitespaceTokenParser getInstance() {
        if (instance == null) {
            instance = new WhitespaceTokenParser();
        }
        return instance;
    }

    private WhitespaceTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        char ch = reader.read();
        if (isSpace(ch)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else if (isTab(ch) || isNewline(ch)) {
            return TokenBuilder.next(reader).build(WhitespaceToken::new);
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        while (reader.hasNext()) {
            char ch = reader.next();
            if (ch == ' ') {
                builder.getText().append(ch);
            } else {
                reader.previous();
                break;
            }
        }
        return builder.build(WhitespaceToken::new);
    }

    private boolean isSpace(char ch) {
        return ch == ' ';
    }

    private boolean isTab(char ch) {
        return ch == '\t';
    }

    private boolean isNewline(char ch) {
        return ch == '\n';
    }
}
