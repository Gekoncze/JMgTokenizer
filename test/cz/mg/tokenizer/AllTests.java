package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.services.GlyphProcessorTest;
import cz.mg.tokenizer.services.NewlineProcessorTest;
import cz.mg.tokenizer.services.TokenProcessorTest;

public @Test class AllTests {
    public static void main(String[] args) {
        GlyphProcessorTest.main(args);
        NewlineProcessorTest.main(args);
        TokenProcessorTest.main(args);
    }
}
