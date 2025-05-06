package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.token.Token;

public @Test class TokenBuilderTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenBuilderTest.class.getSimpleName() + " ... ");

        TokenBuilderTest test = new TokenBuilderTest();
        test.testBuildEmpty();
        test.testBuildWithCharacter();
        test.testBuildWithString();
        test.testBuildWithInitialCharacter();
        test.testBuildWithInitialString();
        test.testBuildCombined();

        System.out.println("OK");
    }

    private void testBuildEmpty()
    {
        Token token = new TokenBuilder(11).build(Token::new);
        Assert.assertEquals(11, token.getPosition());
        Assert.assertEquals("", token.getText());
    }

    private void testBuildWithCharacter()
    {
        Token token = new TokenBuilder(12)
            .append('M')
            .build(Token::new);
        Assert.assertEquals(12, token.getPosition());
        Assert.assertEquals("M", token.getText());
    }

    private void testBuildWithString()
    {
        Token token = new TokenBuilder(13)
            .append("Mg")
            .build(Token::new);
        Assert.assertEquals(13, token.getPosition());
        Assert.assertEquals("Mg", token.getText());
    }

    private void testBuildWithInitialCharacter()
    {
        Token token = new TokenBuilder(14, 'a').build(Token::new);
        Assert.assertEquals(14, token.getPosition());
        Assert.assertEquals("a", token.getText());
    }

    private void testBuildWithInitialString()
    {
        Token token = new TokenBuilder(15, "ab").build(Token::new);
        Assert.assertEquals(15, token.getPosition());
        Assert.assertEquals("ab", token.getText());
    }

    private void testBuildCombined()
    {
        Token token = new TokenBuilder(16, 'M')
            .append('g')
            .append("Tokenizer")
            .build(Token::new);
        Assert.assertEquals(16, token.getPosition());
        Assert.assertEquals("MgTokenizer", token.getText());
    }
}
