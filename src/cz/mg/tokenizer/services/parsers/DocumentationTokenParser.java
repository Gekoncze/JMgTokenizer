package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.DoubleQuoteToken;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenBuilder;

public @Service class DocumentationTokenParser implements TokenParser {
    private static @Optional DocumentationTokenParser instance;

    public static @Mandatory DocumentationTokenParser getInstance() {
        if (instance == null) {
            instance = new DocumentationTokenParser();
        }
        return instance;
    }

    private DocumentationTokenParser() {
    }

    @Override
    public @Optional Token parse(@Mandatory CharacterReader reader) {
        if (reader.has('/') && reader.hasNext('*')) {
            return parse(reader, new TokenBuilder(reader.getPosition()));
        } else {
            return null;
        }
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory TokenBuilder builder) {
        reader.next();
        reader.next();
        while (reader.hasNext()) {
            char ch = reader.next();
            if (ch == '*' && reader.hasNext('/')) {
                reader.next();
                break;
            } else {
                builder.getText().append(ch);
            }
        }
        return builder.build(DoubleQuoteToken::new);
    }
}
