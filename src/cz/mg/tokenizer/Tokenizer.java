package cz.mg.tokenizer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.services.parsers.*;
import cz.mg.tokenizer.utilities.CharacterReader;
import cz.mg.tokenizer.utilities.TokenizeException;

public @Service class Tokenizer {
    private static @Optional Tokenizer instance;

    public static Tokenizer getInstance() {
        if (instance == null) {
            instance = new Tokenizer();
            instance.parsers = new List<>(
                CommentTokenParser.getInstance(),
                DocumentationTokenParser.getInstance(),
                SingleQuoteTokenParser.getInstance(),
                DoubleQuoteTokenParser.getInstance(),
                WhitespaceTokenParser.getInstance(),
                NumberTokenParser.getInstance(),
                NameTokenParser.getInstance(),
                SpecialTokenParser.getInstance()
            );
        }
        return instance;
    }

    private List<TokenParser> parsers;

    private Tokenizer() {
    }

    public @Mandatory List<Token> tokenize(@Mandatory String content) {
        return tokenize(content, parsers);
    }

    public @Mandatory List<Token> tokenize(@Mandatory String content, @Mandatory List<TokenParser> parsers) {
        List<Token> tokens = new List<>();
        CharacterReader reader = new CharacterReader(content);
        while (reader.hasPrevious()) {
            tokens.addLast(parse(reader, parsers));
        }
        return tokens;
    }

    private @Mandatory Token parse(@Mandatory CharacterReader reader, @Mandatory List<TokenParser> parsers) {
        for (TokenParser parser : parsers) {
            Token token = parser.parse(reader);
            if (token != null) {
                return token;
            }
        }
        throw new TokenizeException(reader.getPosition(), "Unsupported character.");
    }
}
