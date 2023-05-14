package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.MultiLineCommentToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;
import cz.mg.tokenizer.utilities.TokenizeException;

public @Service class MultiLineCommentTokenParser implements TokenParser {
    private static @Optional MultiLineCommentTokenParser instance;

    public static @Mandatory MultiLineCommentTokenParser getInstance() {
        if (instance == null) {
            instance = new MultiLineCommentTokenParser();
        }
        return instance;
    }

    private MultiLineCommentTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has(this::slash) && reader.hasNext(this::star)) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.read();
        reader.read();
        while (reader.has()) {
            if (reader.has(this::star) && reader.hasNext(this::slash)) {
                reader.read();
                reader.read();
                return builder.build(MultiLineCommentToken::new);
            } else {
                builder.getText().append(reader.read());
            }
        }
        throw new TokenizeException(builder.getPosition(), "Unclosed multiline token.");
    }

    private boolean slash(char ch) {
        return ch == '/';
    }

    private boolean star(char ch) {
        return ch == '*';
    }
}
