package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.components.CharacterReaderTest;
import cz.mg.tokenizer.components.TokenBuilderTest;
import cz.mg.tokenizer.components.TokenReaderTest;
import cz.mg.tokenizer.services.PositionServiceTest;
import cz.mg.tokenizer.services.UserExceptionFactoryTest;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.tokenizer.components
        CharacterReaderTest.main(args);
        TokenBuilderTest.main(args);
        TokenReaderTest.main(args);

        // cz.mg.tokenizer.services
        PositionServiceTest.main(args);
        UserExceptionFactoryTest.main(args);
    }
}
