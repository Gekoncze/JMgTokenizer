package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.services.PositionServiceTest;
import cz.mg.tokenizer.services.parsers.*;
import cz.mg.tokenizer.components.CharacterReaderTest;
import cz.mg.tokenizer.components.TokenReaderTest;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.tokenizer.services.parsers
        BracketTokenParserTest.main(args);
        DoubleQuoteTokenParserTest.main(args);
        MultiLineCommentTokenParserTest.main(args);
        NameTokenParserTest.main(args);
        NumberTokenParserTest.main(args);
        OperatorTokenParserTest.main(args);
        SeparatorTokenParserTest.main(args);
        SingleLineCommentTokenParserTest.main(args);
        SingleQuoteTokenParserTest.main(args);
        SpecialTokenParserTest.main(args);
        WhitespaceTokenParserTest.main(args);

        // cz.mg.tokenizer.services
        PositionServiceTest.main(args);

        // cz.mg.tokenizer.components
        CharacterReaderTest.main(args);
        TokenReaderTest.main(args);

        // cz.mg.tokenizer
        TokenizerTest.main(args);
    }
}
