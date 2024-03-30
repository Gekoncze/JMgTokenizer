package cz.mg.tokenizer.test;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.*;
import cz.mg.tokenizer.entities.tokens.quote.DoubleQuoteToken;
import cz.mg.tokenizer.entities.tokens.comment.MultiLineCommentToken;
import cz.mg.tokenizer.entities.tokens.comment.SingleLineCommentToken;
import cz.mg.tokenizer.entities.tokens.quote.SingleQuoteToken;

public @Service class TokenFactory {
    private static volatile @Service TokenFactory instance;

    public static @Service TokenFactory getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TokenFactory();
                }
            }
        }
        return instance;
    }

    private TokenFactory() {
    }

    public @Mandatory Token token(@Mandatory String text) {
        return new Token(text, 0);
    }

    public @Mandatory CommentToken comment(@Mandatory String text) {
        return new CommentToken(text, 0);
    }

    public @Mandatory MultiLineCommentToken multiLineComment(@Mandatory String text) {
        return new MultiLineCommentToken(text, 0);
    }

    public @Mandatory SingleLineCommentToken singleLineComment(@Mandatory String text) {
        return new SingleLineCommentToken(text, 0);
    }

    public @Mandatory NumberToken number(@Mandatory String text) {
        return new NumberToken(text, 0);
    }

    public @Mandatory QuoteToken quote(@Mandatory String text) {
        return new QuoteToken(text, 0);
    }

    public @Mandatory DoubleQuoteToken doubleQuote(@Mandatory String text) {
        return new DoubleQuoteToken(text, 0);
    }

    public @Mandatory SingleQuoteToken singleQuote(@Mandatory String text) {
        return new SingleQuoteToken(text, 0);
    }

    public @Mandatory SymbolToken symbol(@Mandatory String text) {
        return new SymbolToken(text, 0);
    }

    public @Mandatory WordToken word(@Mandatory String text) {
        return new WordToken(text, 0);
    }

    public @Mandatory WhitespaceToken whitespace(@Mandatory String text) {
        return new WhitespaceToken(text, 0);
    }
}
