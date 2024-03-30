package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.services.PositionServiceTest;
import cz.mg.tokenizer.services.parsers.*;
import cz.mg.tokenizer.components.CharacterReaderTest;
import cz.mg.tokenizer.components.TokenReaderTest;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.tokenizer.services.parsers
        DoubleQuoteTokenParserTest.main(args);
        MultiLineCommentTokenParserTest.main(args);
        WordTokenParserTest.main(args);
        NumberTokenParserTest.main(args);
        SymbolsTokenParserTest.main(args);
        SingleLineCommentTokenParserTest.main(args);
        SingleQuoteTokenParserTest.main(args);
        SymbolTokenParserTest.main(args);
        WhitespaceTokenParserTest.main(args);

        // cz.mg.tokenizer.services
        PositionServiceTest.main(args);

        // cz.mg.tokenizer.components
        CharacterReaderTest.main(args);
        TokenReaderTest.main(args);

        // cz.mg.tokenizer
        CTokenizerTest.main(args);
    }
}
