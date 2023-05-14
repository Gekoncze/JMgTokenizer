package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.services.PositionServiceTest;
import cz.mg.tokenizer.services.parsers.*;
import cz.mg.tokenizer.utilities.CharacterReaderTest;
import cz.mg.tokenizer.utilities.TokenReaderTest;

public @Test class AllTests {
    public static void main(String[] args) {
        CharacterReaderTest.main(args);
        TokenReaderTest.main(args);
        PositionServiceTest.main(args);
        SingleLineCommentTokenParserTest.main(args);
        MultiLineCommentTokenParserTest.main(args);
        DoubleQuoteTokenParserTest.main(args);
        NameTokenParserTest.main(args);
        NumberTokenParserTest.main(args);
        SingleQuoteTokenParserTest.main(args);
        SpecialTokenParserTest.main(args);
        WhitespaceTokenParserTest.main(args);
        TokenizerTest.main(args);
    }
}
