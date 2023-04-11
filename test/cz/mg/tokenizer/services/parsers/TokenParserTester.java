package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Utility;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.services.TokenParser;
import cz.mg.tokenizer.utilities.CharacterReader;

public @Utility class TokenParserTester {
    private final @Mandatory TokenParser parser;
    private final int beforeCount;
    private final int afterCount;

    public TokenParserTester(@Mandatory TokenParser parser, int beforeCount, int afterCount) {
        this.parser = parser;
        this.beforeCount = beforeCount;
        this.afterCount = afterCount;
    }

    public void testParse(@Mandatory String content) {
        CharacterReader reader = new CharacterReader(content);
        for (int i = 0; i < content.length(); i++) {
            Token actualToken = parser.parse(reader);
            Assert.assertNull(actualToken);
        }
    }

    public void testParse(@Mandatory String before, @Mandatory String token, @Mandatory String after) {
        String content = before + token + after;
        CharacterReader reader = new CharacterReader(content);

        int expectedTokenPosition = before.length();
        String expectedTokenText = token.substring(beforeCount, token.length() - afterCount);
        int expectedReaderPosition = (before + token).length();

        for (int i = 0; i < content.length() && i <= expectedTokenPosition; i++) {
            Token actualToken = parser.parse(reader);
            if (i == expectedTokenPosition) {
                Assert.assertNotNull(actualToken);
                Assert.assertThat(expectedTokenText, actualToken.getText())
                    .withPrintFunction(t -> '"' + t + '"')
                    .areEqual();
                Assert.assertEquals(expectedTokenPosition, actualToken.getPosition());
                Assert.assertEquals(expectedReaderPosition, reader.getPosition());
                return;
            } else {
                Assert.assertNull(actualToken);
                reader.read();
            }
        }
        throw new IllegalArgumentException("No token found.");
    }
}