package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.tokenizer.utilities.TokenizeException;

public @Test class DocumentationTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + DocumentationTokenParserTest.class.getSimpleName() + " ... ");

        DocumentationTokenParserTest test = new DocumentationTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(DocumentationTokenParser.getInstance(), 2, 2);
        tester.testParse("");
        Assert.assertThatCode(() -> tester.testParse("/*")).throwsException(TokenizeException.class);
        tester.testParse("int a = 0;");
        tester.testParse("int a = 0; ", "/* test*/", "");
        tester.testParse("int a = 0; ", "/*test*/", "");
        tester.testParse("int a = 0; ", "/*test */", "");
        tester.testParse("", "/*test*/", "");
        tester.testParse("", "/* test*/", "");
        tester.testParse("", "/*test */", "");
        tester.testParse("// test */");
        tester.testParse("(", "/*a*/", "");
        tester.testParse("", "/*test*/", "\nint a = 0;");
        tester.testParse("void", "/* test*/", "\nint a = 0;");
        tester.testParse("", "/*test (*/", "");
        tester.testParse("", "/*test 1*/", "");
        tester.testParse("", "/*test a*/", "");
        tester.testParse("", "/*test //*/", "");
        tester.testParse("", "/*test \"*/", "");
        tester.testParse("", "/*test '*/", "");
    }
}
