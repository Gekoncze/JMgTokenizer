package cz.mg.tokenizer.services.parsers;

import cz.mg.annotations.classes.Test;
import cz.mg.tokenizer.entities.tokens.comment.MultiLineCommentToken;

public @Test class MultiLineCommentTokenParserTest {
    public static void main(String[] args) {
        System.out.print("Running " + MultiLineCommentTokenParserTest.class.getSimpleName() + " ... ");

        MultiLineCommentTokenParserTest test = new MultiLineCommentTokenParserTest();
        test.testParse();

        System.out.println("OK");
    }

    private void testParse() {
        TokenParserTester tester = new TokenParserTester(
            MultiLineCommentTokenParser.getInstance(), 2, 2, MultiLineCommentToken.class
        );
        tester.testParse("");
        tester.testException("/*");
        tester.testParse("int a = 0;");
        tester.testParse("int a = 0; ", "/* test*/", "");
        tester.testParse("int a = 0; ", "/*test*/", "");
        tester.testParse("int a = 0; ", "/*test */", "");
        tester.testParse("", "/*test*/", "");
        tester.testParse("", "/* test*/", "");
        tester.testParse("", "/*test */", "");
        tester.testParse("test */");
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
