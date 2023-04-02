package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class CharacterReaderTest {
    public static void main(String[] args) {
        System.out.print("Running " + CharacterReaderTest.class.getSimpleName() + " ... ");

        /*CharacterReaderTest test = new CharacterReaderTest();
        test.testEmpty();
        test.testSingle();
        test.testMultiple();

        System.out.println("OK");*/
    }

    /*private void testEmpty() {
        CharacterReader reader = new CharacterReader("");

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.has(' '));
        Assert.assertEquals(false, reader.has('a'));
        Assert.assertEquals(false, reader.has(ch -> true));
        Assert.assertEquals(false, reader.has(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasBefore());
        Assert.assertEquals(false, reader.hasBefore(' '));
        Assert.assertEquals(false, reader.hasBefore('a'));
        Assert.assertEquals(false, reader.hasBefore(ch -> true));
        Assert.assertEquals(false, reader.hasBefore(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasAfter());
        Assert.assertEquals(false, reader.hasAfter(' '));
        Assert.assertEquals(false, reader.hasAfter('a'));
        Assert.assertEquals(false, reader.hasAfter(ch -> true));
        Assert.assertEquals(false, reader.hasAfter(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionThrown(TokenizeException.class, reader::read);
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionThrown(TokenizeException.class, reader::next);
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionThrown(TokenizeException.class, reader::previous);
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate(ch -> false));

        Assert.assertEquals(0, reader.getPosition());
    }

    private void testSingle() {
        CharacterReader reader = new CharacterReader("a");

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(true, reader.has());
        Assert.assertEquals(false, reader.has(' '));
        Assert.assertEquals(true, reader.has('a'));
        Assert.assertEquals(true, reader.has(ch -> true));
        Assert.assertEquals(false, reader.has(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasBefore());
        Assert.assertEquals(false, reader.hasBefore(' '));
        Assert.assertEquals(false, reader.hasBefore('a'));
        Assert.assertEquals(false, reader.hasBefore(ch -> true));
        Assert.assertEquals(false, reader.hasBefore(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasAfter());
        Assert.assertEquals(false, reader.hasAfter(' '));
        Assert.assertEquals(false, reader.hasAfter('a'));
        Assert.assertEquals(false, reader.hasAfter(ch -> true));
        Assert.assertEquals(false, reader.hasAfter(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read());
        Assert.assertEquals('a', reader.read('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(' '));
        Assert.assertEquals('a', reader.read(ch -> true));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.next());
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::next);
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::previous);
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.next('a'));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.next(ch -> true));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.next(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.previous());
        Assert.assertEquals(-1, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::previous);
        Assert.assertEquals(-1, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::next);
        Assert.assertEquals(-1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.previous('a'));
        Assert.assertEquals(-1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.previous(ch -> true));
        Assert.assertEquals(-1, reader.getPosition());
        reader.reset();

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.previous(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionNotThrown(() -> reader.validate('a'));
        Assert.assertExceptionNotThrown(() -> reader.validate(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.validate(ch -> false));

        Assert.assertEquals(0, reader.getPosition());
    }

    private void testMultiple() {
        CharacterReader reader = new CharacterReader("a\n ");

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.next());
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertEquals('\n', reader.next());
        Assert.assertEquals(2, reader.getPosition());
        Assert.assertEquals(' ', reader.next());
        Assert.assertEquals(3, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::next);
        Assert.assertEquals(3, reader.getPosition());
        reader.reset();

        reader.next();
        reader.next();

        Assert.assertEquals();
    }*/
}
