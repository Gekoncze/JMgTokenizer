package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Position;

public @Test class PositionServiceTest {
    public static void main(String[] args) {
        System.out.print("Running " + PositionServiceTest.class.getSimpleName() + " ... ");

        PositionServiceTest test = new PositionServiceTest();
        test.testFindInEmpty();
        test.testFindInSingleLine();
        test.testFindInMultipleLines1();
        test.testFindInMultipleLines2();
        test.testFindInMultipleLines3();

        System.out.println("OK");
    }

    private void testFindInEmpty() {
        PositionService service = PositionService.getInstance();
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", -1));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", 0));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", 1));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", 11));
    }

    private void testFindInSingleLine() {
        String content = "Single line test.";
        PositionService service = PositionService.getInstance();
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", -1000));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", -1));
        Assert.assertEquals(new Position(1, 1), service.find(content, 0), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 2), service.find(content, 1), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 3), service.find(content, 2), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 7), service.find(content, 6), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 17), service.find(content, 16), this::compare, this::toString);
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", 17));
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find("", 1000));
    }

    private void testFindInMultipleLines1() {
        String content = "Multi line test\nof position service\n.";
        PositionService service = PositionService.getInstance();
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find(content, -1));
        Assert.assertEquals(new Position(1, 1), service.find(content, 0), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 2), service.find(content, 1), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 15), service.find(content, 14), this::compare, this::toString);
        Assert.assertEquals(new Position(1, 16), service.find(content, 15), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 1), service.find(content, 16), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 2), service.find(content, 17), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 3), service.find(content, 18), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 19), service.find(content, 34), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 20), service.find(content, 35), this::compare, this::toString);
        Assert.assertEquals(new Position(3, 1), service.find(content, 36), this::compare, this::toString);
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find(content, 37));
    }

    private void testFindInMultipleLines2() {
        PositionService service = PositionService.getInstance();
        Assert.assertEquals(new Position(1, 1), service.find("\n", 0), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 1), service.find("\n\n", 1), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 1), service.find("\n\n\n", 1), this::compare, this::toString);
        Assert.assertEquals(new Position(3, 1), service.find("\n\n\n", 2), this::compare, this::toString);
    }

    private void testFindInMultipleLines3() {
        String content = "\n multi line \n";
        PositionService service = PositionService.getInstance();
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find(content, -1));
        Assert.assertEquals(new Position(1, 1), service.find(content, 0), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 1), service.find(content, 1), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 2), service.find(content, 2), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 12), service.find(content, 12), this::compare, this::toString);
        Assert.assertEquals(new Position(2, 13), service.find(content, 13), this::compare, this::toString);
        Assert.assertExceptionThrown(ArrayIndexOutOfBoundsException.class, () -> service.find(content, 14));
    }

    private boolean compare(@Mandatory Position a, @Mandatory Position b) {
        return a.getRow() == b.getRow() && a.getColumn() == b.getColumn();
    }

    private @Mandatory String toString(@Mandatory Position p) {
        return "(" + p.getRow() + "," + p.getColumn() + ")";
    }
}
