package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;

public @Test class NameTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + NameTokenParserTest.class.getSimpleName() + " ... ");

        NameTokenParserTest test = new NameTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(NameTokenParser.getInstance(), 0, 0);
        tester.testParse("");
        tester.testParse("1");
        tester.testParse("3.14");
        tester.testParse("+");
        tester.testParse("", "_", "");
        tester.testParse("", "a", "");
        tester.testParse(",", "_a_", ",");
        tester.testParse("(", "a", ")");
        tester.testParse(" ", "a", " ");
        tester.testParse("8", "a", "*");
        tester.testParse("-1", "foo", " bar");
        tester.testParse("-", "fooBar", "");
        tester.testParse("", "Bar2foo", "(");
        tester.testParse("#", "Bare_Foo", "%");
        tester.testParse(",", "abcdefghijklmnopqrstuvwxyz", ",");
        tester.testParse(",", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", ",");
        tester.testParse(",", "_0123456789", ",");
    }
}
