package cz.mg.tokenizer;

import cz.mg.annotations.classes.Component;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.services.parsers.*;
import cz.mg.tokenizer.components.CharacterReader;
import cz.mg.tokenizer.exceptions.TokenizeException;

public @Component class Tokenizer {
    private final @Mandatory List<TokenParser> parsers;

    public Tokenizer() {
        this(
            SingleLineCommentTokenParser.getInstance(),
            MultiLineCommentTokenParser.getInstance(),
            SingleQuoteTokenParser.getInstance(),
            DoubleQuoteTokenParser.getInstance(),
            WhitespaceTokenParser.getInstance(),
            NumberTokenParser.getInstance(),
            NameTokenParser.getInstance(),
            BracketTokenParser.getInstance(),
            SpecialTokenParser.getInstance()
        );
    }

    public Tokenizer(TokenParser... parsers) {
        this.parsers = new List<>(parsers);
    }

    public Tokenizer(@Mandatory Iterable<TokenParser> parsers) {
        this.parsers = new List<>(parsers);
    }

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
        throw new TokenizeException(reader.getPosition(), "Unsupported character.");
    }
}
