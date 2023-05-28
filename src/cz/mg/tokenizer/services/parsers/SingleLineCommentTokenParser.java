package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.SingleLineCommentToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.components.TokenBuilder;

public @Service class SingleLineCommentTokenParser implements TokenParser {
    private static volatile @Service SingleLineCommentTokenParser instance;

    public static @Service SingleLineCommentTokenParser getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SingleLineCommentTokenParser();
                }
            }
        }
        return instance;
    }

    private SingleLineCommentTokenParser() {
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
        reader.read();
        reader.read();
        while (reader.has()) {
            if (reader.has(this::newline)) {
                break;
            } else {
                builder.getText().append(reader.read());
            }
        }
        return builder.build(SingleLineCommentToken::new);
    }

    private boolean slash(char ch) {
        return ch == '/';
    }

    private boolean newline(char ch) {
        return ch == '\n';
    }
}
