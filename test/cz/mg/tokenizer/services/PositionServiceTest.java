package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;

public @Test class PositionServiceTest {
    public static void main(String[] args) {
        System.out.print("Running" + PositionServiceTest.class.getSimpleName() + " ... ");

        PositionServiceTest test = new PositionServiceTest();
        test.testFindInEmpty();
        test.testFindInSingleLine();
        test.testFindInMultipleLines();

        System.out.println("OK");
    }

    private void testFindInEmpty() {
        // TODO
    }

    private void testFindInSingleLine() {
        // TODO
    }

    private void testFindInMultipleLines() {
        // TODO
    }
}
