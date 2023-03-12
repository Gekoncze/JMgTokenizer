package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.DoubleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class CommentTokenParser implements TokenParser {
    private static @Optional CommentTokenParser instance;

    public static @Mandatory CommentTokenParser getInstance() {
        if (instance == null) {
            instance = new CommentTokenParser();
        }
        return instance;
    }

    private CommentTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::slash) && reader.hasNext(this::slash)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.next();
        reader.next();
        while (reader.has()) {
            if (reader.has(this::newline)) {
                break;
            } else {
                builder.getText().append(reader.next());
            }
        }
        return builder.build(DoubleQuoteToken::new);
    }

    private boolean slash(char ch) {
        return ch == '/';
    }

    private boolean newline(char ch) {
        return ch == '\n';
    }
}
