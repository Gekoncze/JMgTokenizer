package cz.mg.tokenizer;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.exceptions.TokenizeException;
import cz.mg.tokenizer.components.TokenParser;

public @Component class SimpleTokenizer implements Tokenizer {
    private final @Mandatory List<TokenParser> parsers;

    public SimpleTokenizer(TokenParser... parsers) {
        this.parsers = new List<>(parsers);
    }

    public SimpleTokenizer(@Mandatory Iterable<TokenParser> parsers) {
        this.parsers = new List<>(parsers);
    }

    @Override
    public @Mandatory List<Token> tokenize(@Mandatory String content) {
        List<Token> tokens = new List<>();
        CharacterReader reader = new CharacterReader(content);
        while (reader.has()) {
            tokens.addLast(parse(reader));
        }
        return tokens;
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader) {
        for (TokenParser parser : parsers) {
            Token token = parser.parse(reader);
            if (token != null) {
                return token;
            }
        }
        throw new TokenizeException(reader.getPosition(), "Unexpected character '" + reader.get() + "'.");
    }
}
