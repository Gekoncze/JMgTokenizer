package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.tokenizer.exceptions.TokenizeException;

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

        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read('a')).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(' ')).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(ch -> true)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(ch -> false)).throwsException(TokenizeException.class);

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
        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('a'));
        Assert.assertEquals(true, reader.hasPrevious(ch -> ch == 'a'));
        Assert.assertEquals(false, reader.hasNext());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read('a'));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertEquals('a', reader.read(ch -> true));
        Assert.assertEquals(1, reader.getPosition());
        reader.reset();

        Assert.assertEquals(0, reader.getPosition());

        Assert.assertThatCode(() -> reader.read(' ')).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(ch -> false)).throwsException(TokenizeException.class);

        Assert.assertEquals(0, reader.getPosition());
    }

    private void testMultiple() {
        CharacterReader reader = new CharacterReader("a\n ");

        Assert.assertEquals(0, reader.getPosition());
        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(ch -> true));
        Assert.assertEquals(true, reader.has('a'));
        Assert.assertEquals(true, reader.has(ch -> ch == 'a'));
        Assert.assertEquals(true, reader.hasNext('\n'));
        Assert.assertEquals(true, reader.hasNext(ch -> ch == '\n'));

        Assert.assertThatCode(() -> reader.read('\n')).throwsException(TokenizeException.class);
        Assert.assertEquals('a', reader.read());
        Assert.assertEquals(1, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('a'));
        Assert.assertEquals(true, reader.hasPrevious(ch -> ch == 'a'));
        Assert.assertEquals(true, reader.has('\n'));
        Assert.assertEquals(true, reader.has(ch -> ch == '\n'));
        Assert.assertEquals(true, reader.hasNext(' '));
        Assert.assertEquals(true, reader.hasNext(ch -> ch == ' '));

        Assert.assertThatCode(() -> reader.read('a')).throwsException(TokenizeException.class);
        Assert.assertEquals('\n', reader.read());
        Assert.assertEquals(2, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious('\n'));
        Assert.assertEquals(true, reader.hasPrevious(ch -> ch == '\n'));
        Assert.assertEquals(true, reader.has(' '));
        Assert.assertEquals(true, reader.has(ch -> ch == ' '));
        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(ch -> true));

        Assert.assertThatCode(() -> reader.read('1')).throwsException(TokenizeException.class);
        Assert.assertEquals(' ', reader.read());
        Assert.assertEquals(3, reader.getPosition());
        Assert.assertEquals(true, reader.hasPrevious(' '));
        Assert.assertEquals(true, reader.hasPrevious(ch -> ch == ' '));
        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.has(ch -> true));
        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(ch -> true));

        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertEquals(3, reader.getPosition());

        reader.reset();

        Assert.assertEquals('a', reader.read('a'));
        Assert.assertEquals('\n', reader.read('\n'));
        Assert.assertEquals(' ', reader.read(' '));

        reader.reset();

        Assert.assertEquals('a', reader.read(ch -> true));
        Assert.assertEquals('\n', reader.read(ch -> true));
        Assert.assertEquals(' ', reader.read(ch -> true));

        Assert.assertEquals(
            2, Assert.assertThatCode(reader::read).throwsException(TokenizeException.class).getPosition()
        );

        reader.reset();

        Assert.assertEquals(
            0, Assert.assertThatCode(() -> reader.read('?')).throwsException(TokenizeException.class).getPosition()
        );
    }
}
