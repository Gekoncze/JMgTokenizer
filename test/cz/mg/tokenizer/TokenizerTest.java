package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.*;

import java.util.Objects;

public @Test class TokenizerTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenizerTest.class.getSimpleName() + " ... ");

        TokenizerTest test = new TokenizerTest();
        test.testTokenize();

        System.out.println("OK");
    }

    private void testTokenize() {
        testTokenize(
            "int a = 0; // test",
            new NameToken("int", 0),
            new WhitespaceToken(" ", 3),
            new NameToken("a", 4),
            new WhitespaceToken(" ", 5),
            new SpecialToken("=", 6),
            new WhitespaceToken(" ", 7),
            new NumberToken("0", 8),
            new SpecialToken(";", 9),
            new WhitespaceToken(" ", 10),
            new SingleLineCommentToken(" test", 11)
        );

        testTokenize(
            "char* text=\"a 1 + '\";//comment of the day\naha/*a 1 + '*/hah",
            new NameToken("char", 0),
            new SpecialToken("*", 4),
            new WhitespaceToken(" ", 5),
            new NameToken("text", 6),
            new SpecialToken("=", 10),
            new DoubleQuoteToken("a 1 + '", 11),
            new SpecialToken(";", 20),
            new SingleLineCommentToken("comment of the day", 21),
            new WhitespaceToken("\n", 41),
            new NameToken("aha", 42),
            new MultiLineCommentToken("a 1 + '", 45),
            new NameToken("hah", 56)
        );

        testTokenize(
            "1*(3.14/[0x32])-0.1",
            new NumberToken("1", 0),
            new SpecialToken("*", 1),
            new BracketToken("(", 2),
            new NumberToken("3.14", 3),
            new SpecialToken("/", 7),
            new BracketToken("[", 8),
            new NumberToken("0x32", 9),
            new BracketToken("]", 13),
            new BracketToken(")", 14),
            new SpecialToken("-", 15),
            new NumberToken("0.1", 16)
        );
    }

    private void testTokenize(@Mandatory String content, Token... expectedTokens) {
        testTokenize(content, new List<>(expectedTokens));
    }

    private void testTokenize(@Mandatory String content, @Mandatory List<Token> expectedTokens) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> actualTokens = tokenizer.tokenize(content);
        Assert.assertThatCollections(expectedTokens, actualTokens)
            .withCompareFunction(this::compare)
            .withPrintFunction(this::print)
            .areEqual();
    }


    private boolean compare(@Mandatory Token a, @Mandatory Token b) {
        return Objects.equals(a.getText(), b.getText())
            && Objects.equals(a.getPosition(), b.getPosition())
            && Objects.equals(a.getClass(), b.getClass());
    }

    private @Mandatory String print(@Mandatory Token token) {
        return token.getClass().getSimpleName() + "(\"" + token.getText() + "\"," + token.getPosition() + ")";
    }
}
