package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.entities.tokens.OperatorToken;

public @Test class OperatorTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + OperatorTokenParserTest.class.getSimpleName() + " ... ");

        OperatorTokenParserTest test = new OperatorTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(
            OperatorTokenParser.getInstance(), 0, 0, OperatorToken.class
        );
        tester.testParse("");
        tester.testParse("1");
        tester.testParse("foo");
        tester.testParse("_");
        tester.testParse("@");
        tester.testParse(",");
        tester.testParse("(");
        tester.testParse("", "+", "");
        tester.testParse("", "=", "");
        tester.testParse("", "~", "");
        tester.testParse("", "!", "");
        tester.testParse("", "?:", "");
        tester.testParse("", "++", "");
        tester.testParse("", ">=", "");
        tester.testParse("3", "-", "1");
        tester.testParse("a", "<", "2");
        tester.testParse("B", "?", "3");
        tester.testParse("1", "==", "A");
        tester.testParse("2", "*", "b");
        tester.testParse(" ", "/=", " ");
    }
}
