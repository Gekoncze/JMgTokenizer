package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.TokenType;
import cz.mg.tokenizer.services.processors.TokenProcessor;

public @Test class TokenProcessorTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenProcessorTest.class.getSimpleName() + " ... ");

        TokenProcessorTest test = new TokenProcessorTest();
        test.testProcessing();

        System.out.println("OK");
    }

    private void testProcessing() {
        testProcessing(
            new List<>(new Glyph('f', 0), new Glyph('o', 1), new Glyph('o', 2)),
            new List<>(new Token(TokenType.NAME, "foo", 0))
        );

        testProcessing(
            new List<>(new Glyph('(', 0), new Glyph('b', 1), new Glyph('a', 2), new Glyph('r', 3), new Glyph(')', 4)),
            new List<>(
                new Token(TokenType.SPECIAL, "(", 0),
                new Token(TokenType.NAME, "bar", 1),
                new Token(TokenType.SPECIAL, ")", 4)
            )
        );

        testProcessing(
            new List<>(new Glyph('2', 0), new Glyph('.', 1), new Glyph('1', 2), new Glyph(';', 3)),
            new List<>(new Token(TokenType.NUMBER, "2.1", 0), new Token(TokenType.SPECIAL, ";", 3))
        );

        // TODO test foo2bar
        // TODO test integer numbers
        // TODO test float and double numbers
        // TODO test double quotes
        // TODO test single quotes
        // TODO test multi line comment
        // TODO test single line comment
        // TODO test multiple specials
    }

    private void testProcessing(List<Glyph> glyphs, List<Token> expectedTokens) {
        TokenProcessor processor = TokenProcessor.getInstance();
        List<List<Token>> lines = processor.process(new List<List<Glyph>>(glyphs));

        Assert.assertEquals(1, lines.count());

        List<Token> tokens = lines.getFirst();
        Assert.assertEquals(
            expectedTokens,
            tokens,
            (e, r) -> e.getType() == r.getType() && e.getText().equals(r.getText()) && e.getPosition() == r.getPosition(),
            token -> "(" + token.getType() + ",'" + token.getText() + "'," + token.getPosition() + ")"
        );
    }
}
