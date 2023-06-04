package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.entities.tokens.SeparatorToken;

public @Test class SeparatorTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + SeparatorTokenParserTest.class.getSimpleName() + " ... ");

        SeparatorTokenParserTest test = new SeparatorTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(
            SeparatorTokenParser.getInstance(), 0, 0, SeparatorToken.class
        );
        tester.testParse("");
        tester.testParse("1");
        tester.testParse("foo");
        tester.testParse("_");
        tester.testParse("@");
        tester.testParse("+");
        tester.testParse("(");
        tester.testParse("", ",", "");
        tester.testParse("", ";", "");
        tester.testParse("3", ",", "1");
        tester.testParse("a", ";", "2");
        tester.testParse("B", ",", "");
        tester.testParse("1", ";", "");
    }
}
