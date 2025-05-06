package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;
import cz.mg.test.components.UnsafeRunnable;
import cz.mg.token.Position;

public @Test class PositionServiceTest {
    public static void main(String[] args) {
        System.out.print("Running " + PositionServiceTest.class.getSimpleName() + " ... ");

        PositionServiceTest test = new PositionServiceTest();
        test.testFindInEmptyLine();
        test.testFindInSingleCharacterLine();
        test.testFindInSingleLine();
        test.testFindInMultipleLines();
        test.testFindInMultipleEmptyLines();
        test.testFindInMultipleLinesWithWeirdSpaces();

        System.out.println("OK");
    }

    private final @Service PositionService service = PositionService.getInstance();

    private void testFindInEmptyLine() {
        assertThrows(() -> service.find("", -11));
        assertThrows(() -> service.find("", -1));
        assertEquals(new Position(1, 1), service.find("", 0));
        assertThrows(() -> service.find("", 1));
        assertThrows(() -> service.find("", 11));
    }

    private void testFindInSingleCharacterLine() {
        assertThrows(() -> service.find("a", -11));
        assertThrows(() -> service.find("a", -1));
        assertEquals(new Position(1, 1), service.find("a", 0));
        assertEquals(new Position(1, 2), service.find("a", 1));
        assertThrows(() -> service.find("a", 2));
        assertThrows(() -> service.find("a", 11));
    }

    private void testFindInSingleLine() {
        String content = "Single line test.";
        assertThrows(() -> service.find("", -1000));
        assertThrows(() -> service.find("", -1));
        assertEquals(new Position(1, 1), service.find(content, 0));
        assertEquals(new Position(1, 2), service.find(content, 1));
        assertEquals(new Position(1, 3), service.find(content, 2));
        assertEquals(new Position(1, 7), service.find(content, 6));
        assertEquals(new Position(1, 17), service.find(content, 16));
        assertThrows(() -> service.find("", 17));
        assertThrows(() -> service.find("", 1000));
    }

    private void testFindInMultipleLines() {
        String content = "Multi line test\nof position service\n.";
        assertThrows(() -> service.find(content, -1));
        assertEquals(new Position(1, 1), service.find(content, 0));
        assertEquals(new Position(1, 2), service.find(content, 1));
        assertEquals(new Position(1, 15), service.find(content, 14));
        assertEquals(new Position(1, 16), service.find(content, 15));
        assertEquals(new Position(2, 1), service.find(content, 16));
        assertEquals(new Position(2, 2), service.find(content, 17));
        assertEquals(new Position(2, 3), service.find(content, 18));
        assertEquals(new Position(2, 19), service.find(content, 34));
        assertEquals(new Position(2, 20), service.find(content, 35));
        assertEquals(new Position(3, 1), service.find(content, 36));
        assertEquals(new Position(3, 2), service.find(content, 37));
        assertThrows(() -> service.find(content, 38));
    }

    private void testFindInMultipleEmptyLines() {
        assertEquals(new Position(1, 1), service.find("\n", 0));
        assertEquals(new Position(2, 1), service.find("\n", 1));
        assertEquals(new Position(1, 1), service.find("\n\n", 0));
        assertEquals(new Position(2, 1), service.find("\n\n", 1));
        assertEquals(new Position(3, 1), service.find("\n\n", 2));
        assertEquals(new Position(1, 1), service.find("\n\n\n", 0));
        assertEquals(new Position(2, 1), service.find("\n\n\n", 1));
        assertEquals(new Position(3, 1), service.find("\n\n\n", 2));
        assertEquals(new Position(4, 1), service.find("\n\n\n", 3));
    }

    private void testFindInMultipleLinesWithWeirdSpaces() {
        String content = "\n multi line \n";
        assertThrows(() -> service.find(content, -1));
        assertEquals(new Position(1, 1), service.find(content, 0));
        assertEquals(new Position(2, 1), service.find(content, 1));
        assertEquals(new Position(2, 2), service.find(content, 2));
        assertEquals(new Position(2, 12), service.find(content, 12));
        assertEquals(new Position(2, 13), service.find(content, 13));
        assertEquals(new Position(3, 1), service.find(content, 14));
        assertThrows(() -> service.find(content, 15));
    }

    private void assertEquals(@Mandatory Position a, @Mandatory Position b) {
        Assert
            .assertThat(a, b)
            .withCompareFunction(this::compare)
            .withPrintFunction(this::toString)
            .areEqual();
    }

    private void assertThrows(@Mandatory UnsafeRunnable runnable) {
        Assert
            .assertThatCode(runnable)
            .throwsException(ArrayIndexOutOfBoundsException.class);
    }

    private boolean compare(@Mandatory Position a, @Mandatory Position b) {
        return a.getRow() == b.getRow() && a.getColumn() == b.getColumn();
    }

    private @Mandatory String toString(@Mandatory Position p) {
        return "(" + p.getRow() + "," + p.getColumn() + ")";
    }
}
