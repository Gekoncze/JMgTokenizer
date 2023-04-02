package cz.mg.tokenizer.utilities;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;

public @Test class CharacterReaderTest {
    public static void main(String[] args) {
        System.out.print("Running " + CharacterReaderTest.class.getSimpleName() + " ... ");

        CharacterReaderTest test = new CharacterReaderTest();
        test.testEmpty();
        test.testSingle();
        test.testMultiple();

        System.out.println("OK");
    }

    private void testEmpty() {
        CharacterReader reader = new CharacterReader("");

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.has(' '));
        Assert.assertEquals(false, reader.has('a'));
        Assert.assertEquals(false, reader.has(ch -> true));
        Assert.assertEquals(false, reader.has(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(' '));
        Assert.assertEquals(false, reader.hasNext('a'));
        Assert.assertEquals(false, reader.hasNext(ch -> true));
        Assert.assertEquals(false, reader.hasNext(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(' '));
        Assert.assertEquals(false, reader.hasPrevious('a'));
        Assert.assertEquals(false, reader.hasPrevious(ch -> true));
        Assert.assertEquals(false, reader.hasPrevious(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasAt(-1));
        Assert.assertEquals(false, reader.hasAt(0));
        Assert.assertEquals(false, reader.hasAt(1));
        Assert.assertEquals(false, reader.hasAt(' ', 0));
        Assert.assertEquals(false, reader.hasAt('a', 0));
        Assert.assertEquals(false, reader.hasAt(ch -> true, 0));
        Assert.assertEquals(false, reader.hasAt(ch -> false, 0));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertExceptionThrown(TokenizeException.class, reader::read);
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read('a'));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(ch -> true));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(ch -> false));

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

        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(' '));
        Assert.assertEquals(false, reader.hasNext('a'));
        Assert.assertEquals(false, reader.hasNext(ch -> true));
        Assert.assertEquals(false, reader.hasNext(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(' '));
        Assert.assertEquals(false, reader.hasPrevious('a'));
        Assert.assertEquals(false, reader.hasPrevious(ch -> true));
        Assert.assertEquals(false, reader.hasPrevious(ch -> false));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals(true, reader.hasAt(0));
        Assert.assertEquals(false, reader.hasAt(' ', 0));
        Assert.assertEquals(false, reader.hasAt('a', -1));
        Assert.assertEquals(true, reader.hasAt('a', 0));
        Assert.assertEquals(false, reader.hasAt('a', -1));
        Assert.assertEquals(false, reader.hasAt(ch -> true, -1));
        Assert.assertEquals(true, reader.hasAt(ch -> true, 0));
        Assert.assertEquals(false, reader.hasAt(ch -> true, 1));
        Assert.assertEquals(false, reader.hasAt(ch -> false, 0));

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read());
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertExceptionThrown(TokenizeException.class, reader::read);
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('a'));
        Assert.assertEquals(true, reader.hasPrevious(ch -> ch == 'a'));
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read('a'));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read(ch -> true));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(' '));
        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read(ch -> false));

        Assert.assertEquals(0, reader.getPosition());
    }

    private void testMultiple() {
        CharacterReader reader = new CharacterReader("a\n ");

        Assert.assertEquals(0, reader.getPosition());
        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(true, reader.has('a'));
        Assert.assertEquals(true, reader.hasNext('\n'));

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read('\n'));
        Assert.assertEquals('a', reader.read('a'));
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('a'));
        Assert.assertEquals(true, reader.has('\n'));
        Assert.assertEquals(true, reader.hasNext(' '));

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read('a'));
        Assert.assertEquals('\n', reader.read('\n'));
        Assert.assertEquals(2, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('\n'));
        Assert.assertEquals(true, reader.has(' '));
        Assert.assertEquals(false, reader.hasNext());

        Assert.assertExceptionThrown(TokenizeException.class, () -> reader.read('1'));
        Assert.assertEquals(' ', reader.read(' '));
        Assert.assertEquals(3, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious(' '));
        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.hasNext());

        Assert.assertExceptionThrown(TokenizeException.class, reader::read);
        Assert.assertEquals(3, reader.getPosition());
    }
}
