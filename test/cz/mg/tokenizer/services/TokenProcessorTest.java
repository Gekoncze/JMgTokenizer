package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.TokenType;
import cz.mg.tokenizer.services.processors.TokenProcessor;
import cz.mg.tokenizer.utilities.TokenizeException;

public @Test class TokenProcessorTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenProcessorTest.class.getSimpleName() + " ... ");

        TokenProcessorTest test = new TokenProcessorTest();
        test.testProcessingBlank();
        test.testProcessingNames();
        test.testProcessingNumbers();
        test.testProcessingSingleQuotes();
        test.testProcessingDoubleQuotes();
        test.testProcessingSpecials();

        System.out.println("OK");
    }

    private void testProcessingBlank() {
        testProcessingSingleLine(new List<>(), new List<>());
        testProcessingSingleLine(new List<>(new Glyph(' ', 0)), new List<>());
    }

    private void testProcessingNames() {
        testProcessingSingleLine(
            new List<>(new Glyph('a', 0)),
            new List<>(new Token(TokenType.NAME, "a", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('f', 0), new Glyph('o', 1), new Glyph('o', 2)),
            new List<>(new Token(TokenType.NAME, "foo", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('(', 0), new Glyph('b', 1), new Glyph('a', 2), new Glyph('r', 3), new Glyph(')', 4)),
            new List<>(
                new Token(TokenType.SPECIAL, "(", 0),
                new Token(TokenType.NAME, "bar", 1),
                new Token(TokenType.SPECIAL, ")", 4)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('f', 0), new Glyph('o', 1), new Glyph('o', 2),
                new Glyph('2', 3),
                new Glyph('f', 4), new Glyph('o', 5), new Glyph('o', 6)
            ),
            new List<>(
                new Token(TokenType.NAME, "foo2foo", 0)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('f', 0), new Glyph('o', 1), new Glyph('o', 2),
                new Glyph('.', 3),
                new Glyph('b', 4), new Glyph('a', 5), new Glyph('r', 6)
            ),
            new List<>(
                new Token(TokenType.NAME, "foo", 0),
                new Token(TokenType.SPECIAL, ".", 3),
                new Token(TokenType.NAME, "bar", 4)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('f', 0), new Glyph('o', 1), new Glyph('o', 2), new Glyph('(', 3)),
            new List<>(
                new Token(TokenType.NAME, "foo", 0),
                new Token(TokenType.SPECIAL, "(", 3)
            )
        );
    }

    private void testProcessingNumbers() {
        testProcessingSingleLine(
            new List<>(new Glyph('6', 0), new Glyph('9', 1)),
            new List<>(new Token(TokenType.NUMBER, "69", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('3', 0), new Glyph('.', 1), new Glyph('1', 2), new Glyph('4', 3)),
            new List<>(new Token(TokenType.NUMBER, "3.14", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('3', 0), new Glyph('.', 1), new Glyph('1', 2), new Glyph('4', 3), new Glyph('f', 4)),
            new List<>(new Token(TokenType.NUMBER, "3.14f", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('3', 0), new Glyph('.', 1), new Glyph('1', 2), new Glyph('4', 3), new Glyph('F', 4)),
            new List<>(new Token(TokenType.NUMBER, "3.14F", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('0', 0), new Glyph('x', 1), new Glyph('A', 2), new Glyph('4', 3)),
            new List<>(new Token(TokenType.NUMBER, "0xA4", 0))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('-', 0),
                new Glyph('3', 1), new Glyph('.', 2), new Glyph('1', 3), new Glyph('4', 4),
                new Glyph(';', 5)
            ),
            new List<>(
                new Token(TokenType.SPECIAL, "-", 0),
                new Token(TokenType.NUMBER, "3.14", 1),
                new Token(TokenType.SPECIAL, ";", 5)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('9', 0), new Glyph('(', 1),
                new Glyph('1', 2), new Glyph('/', 3), new Glyph('2', 4),
                new Glyph(')', 5)
            ),
            new List<>(
                new Token(TokenType.NUMBER, "9", 0),
                new Token(TokenType.SPECIAL, "(", 1),
                new Token(TokenType.NUMBER, "1", 2),
                new Token(TokenType.SPECIAL, "/", 3),
                new Token(TokenType.NUMBER, "2", 4),
                new Token(TokenType.SPECIAL, ")", 5)
            )
        );

        // TODO test multi line comment
        // TODO test single line comment
    }

    private void testProcessingSingleQuotes() {
        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('\'', 1)),
            new List<>(new Token(TokenType.CHARACTER, "", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('"', 1), new Glyph('\'', 2)),
            new List<>(new Token(TokenType.CHARACTER, "\"", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('a', 1), new Glyph('\'', 2)),
            new List<>(new Token(TokenType.CHARACTER, "a", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('\\', 1), new Glyph('a', 2), new Glyph('\'', 3)),
            new List<>(new Token(TokenType.CHARACTER, "\\a", 0))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('\'', 0),
                new Glyph('a', 1),
                new Glyph(' ', 2),
                new Glyph('.', 3),
                new Glyph('6', 4),
                new Glyph('\'', 5)
            ),
            new List<>(new Token(TokenType.CHARACTER, "a .6", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('a', 1), new Glyph('\'', 2), new Glyph('a', 3)),
            new List<>(
                new Token(TokenType.CHARACTER, "a", 0),
                new Token(TokenType.NAME, "a", 3)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('a', 0), new Glyph('\'', 1), new Glyph('a', 2), new Glyph('\'', 3)),
            new List<>(
                new Token(TokenType.NAME, "a", 0),
                new Token(TokenType.CHARACTER, "a", 1)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('\\', 1), new Glyph('n', 2), new Glyph('\'', 2)),
            new List<>(new Token(TokenType.CHARACTER, "\\n", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('\\', 1), new Glyph('\'', 2), new Glyph('\'', 3)),
            new List<>(new Token(TokenType.CHARACTER, "\\'", 0))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('\'', 0),
                new Glyph('\\', 1),
                new Glyph('\'', 2),
                new Glyph('a', 3),
                new Glyph('\'', 4)
            ),
            new List<>(new Token(TokenType.CHARACTER, "\\'a", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('\'', 0), new Glyph('(', 1), new Glyph('\'', 2), new Glyph(')', 3)),
            new List<>(
                new Token(TokenType.CHARACTER, "(", 0),
                new Token(TokenType.SPECIAL, ")", 3)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('(', 0), new Glyph('\'', 1), new Glyph(')', 2), new Glyph('\'', 3)),
            new List<>(
                new Token(TokenType.SPECIAL, "(", 0),
                new Token(TokenType.CHARACTER, ")", 1)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('\'', 0),
                new Glyph('/', 1),
                new Glyph('/', 2),
                new Glyph('a', 3),
                new Glyph('\'', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.CHARACTER, "//a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('\'', 0),
                new Glyph('/', 1),
                new Glyph('*', 2),
                new Glyph('a', 3),
                new Glyph('\'', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.CHARACTER, "/*a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('\'', 0),
                new Glyph('*', 1),
                new Glyph('/', 2),
                new Glyph('a', 3),
                new Glyph('\'', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.CHARACTER, "*/a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('\'', 0)),
                new List<>()
            );
        });

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('\'', 0), new Glyph('a', 1)),
                new List<>()
            );
        });

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('a', 0), new Glyph('\'', 1)),
                new List<>()
            );
        });
    }

    private void testProcessingDoubleQuotes() {
        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('"', 1)),
            new List<>(new Token(TokenType.STRING, "", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('\'', 1), new Glyph('"', 2)),
            new List<>(new Token(TokenType.STRING, "'", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('a', 1), new Glyph('"', 2)),
            new List<>(new Token(TokenType.STRING, "a", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('\\', 1), new Glyph('a', 2), new Glyph('"', 3)),
            new List<>(new Token(TokenType.STRING, "\\a", 0))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('"', 0),
                new Glyph('a', 1),
                new Glyph(' ', 2),
                new Glyph('.', 3),
                new Glyph('6', 4),
                new Glyph('"', 5)
            ),
            new List<>(new Token(TokenType.STRING, "a .6", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('a', 1), new Glyph('"', 2), new Glyph('a', 3)),
            new List<>(
                new Token(TokenType.STRING, "a", 0),
                new Token(TokenType.NAME, "a", 3)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('a', 0), new Glyph('"', 1), new Glyph('a', 2), new Glyph('"', 3)),
            new List<>(
                new Token(TokenType.NAME, "a", 0),
                new Token(TokenType.STRING, "a", 1)
            )
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('\\', 1), new Glyph('n', 2), new Glyph('"', 2)),
            new List<>(new Token(TokenType.STRING, "\\n", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('\\', 1), new Glyph('"', 2), new Glyph('"', 2)),
            new List<>(new Token(TokenType.STRING, "\\\"", 0))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('"', 0),
                new Glyph('\\', 1),
                new Glyph('\"', 2),
                new Glyph('a', 3),
                new Glyph('"', 4)
            ),
            new List<>(new Token(TokenType.STRING, "\\\"a", 0))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('"', 0), new Glyph('(', 1), new Glyph('"', 2), new Glyph(')', 3)),
            new List<>(new Token(TokenType.STRING, "(", 0), new Token(TokenType.SPECIAL, ")", 3))
        );

        testProcessingSingleLine(
            new List<>(new Glyph('(', 0), new Glyph('"', 1), new Glyph(')', 2), new Glyph('"', 3)),
            new List<>(new Token(TokenType.SPECIAL, "(", 0), new Token(TokenType.STRING, ")", 1))
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('"', 0),
                new Glyph('/', 1),
                new Glyph('/', 2),
                new Glyph('a', 3),
                new Glyph('"', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.STRING, "//a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('"', 0),
                new Glyph('/', 1),
                new Glyph('*', 2),
                new Glyph('a', 3),
                new Glyph('"', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.STRING, "/*a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        testProcessingSingleLine(
            new List<>(
                new Glyph('"', 0),
                new Glyph('*', 1),
                new Glyph('/', 2),
                new Glyph('a', 3),
                new Glyph('"', 4),
                new Glyph('a', 5)
            ),
            new List<>(
                new Token(TokenType.STRING, "*/a", 0),
                new Token(TokenType.NAME, "a", 5)
            )
        );

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('"', 0)),
                new List<>()
            );
        });

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('"', 0), new Glyph('a', 1)),
                new List<>()
            );
        });

        Assert.assertExceptionThrown(TokenizeException.class, () -> {
            testProcessingSingleLine(
                new List<>(new Glyph('a', 0), new Glyph('"', 1)),
                new List<>()
            );
        });
    }

    private void testProcessingSpecials() {
        testProcessingSingleLine(
            new List<>(new Glyph('+', 0)),
            new List<>(new Token(TokenType.SPECIAL, "+", 0))
        );

        // TODO - add more tests
    }

    private void testProcessingSingleLine(List<Glyph> glyphs, List<Token> expectedTokens) {
        TokenProcessor processor = TokenProcessor.getInstance();
        List<List<Token>> lines = processor.process(new List<List<Glyph>>(glyphs));

        Assert.assertEquals(1, lines.count());

        List<Token> actualTokens = lines.getFirst();
        Assert.assertEquals(
            expectedTokens,
            actualTokens,
            (e, r) -> e.getType() == r.getType() && e.getText().equals(r.getText()) && e.getPosition() == r.getPosition(),
            token -> "(" + token.getType() + ",'" + token.getText() + "'," + token.getPosition() + ")"
        );
    }
}
