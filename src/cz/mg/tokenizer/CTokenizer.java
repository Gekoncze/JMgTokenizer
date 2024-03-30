package cz.mg.tokenizer;

import cz.mg.annotations.classes.Component;
import cz.mg.tokenizer.services.parsers.*;

public @Component class CTokenizer extends SimpleTokenizer {
    public CTokenizer() {
        super(
            SingleLineCommentTokenParser.getInstance(),
            MultiLineCommentTokenParser.getInstance(),
            SingleQuoteTokenParser.getInstance(),
            DoubleQuoteTokenParser.getInstance(),
            WhitespaceTokenParser.getInstance(),
            NumberTokenParser.getInstance(),
            WordTokenParser.getInstance(),
            BracketTokenParser.getInstance(),
            OperatorTokenParser.getInstance(),
            SeparatorTokenParser.getInstance(),
            SpecialTokenParser.getInstance()
        );
    }
}
