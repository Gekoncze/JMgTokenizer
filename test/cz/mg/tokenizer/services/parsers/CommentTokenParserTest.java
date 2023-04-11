package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;

public @Test class CommentTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + CommentTokenParserTest.class.getSimpleName() + " ... ");

        CommentTokenParserTest test = new CommentTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(CommentTokenParser.getInstance(), 2, 0);
        tester.testParse("");
        tester.testParse("int a = 0;");
        tester.testParse("int a = 0; ", "// test", "");
        tester.testParse("int a = 0; ", "//test", "");
        tester.testParse("int a = 0; ", "//test ", "");
        tester.testParse("", "//test", "");
        tester.testParse("", "// test", "");
        tester.testParse("", "//test ", "");
        tester.testParse("/* test */");
        tester.testParse("(", "//a", "");
        tester.testParse("", "// test", "\nint a = 0;");
        tester.testParse("void", "// test", "\nint a = 0;");
    }
}
