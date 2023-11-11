package cz.mg.tokenizer.components;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.CommentToken;
import cz.mg.tokenizer.entities.tokens.WordToken;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.entities.tokens.WhitespaceToken;
import cz.mg.tokenizer.exceptions.TokenizeException;

public @Test class TokenReaderTest {
    public static void main(String[] args) {
        System.out.print("Running " + TokenReaderTest.class.getSimpleName() + " ... ");

        TokenReaderTest test = new TokenReaderTest();
        test.testEmpty();
        test.testSingle();
        test.testMultiple();
        test.testSkip();

        System.out.println("OK");
    }

    private void testEmpty() {
        TokenReader reader = new TokenReader(new List<>(), TokenizeException::new);

        Assert.assertNull(reader.getItem());
        reader.readEnd();

        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.has(""));
        Assert.assertEquals(false, reader.has("a"));
        Assert.assertEquals(false, reader.has("x"));
        Assert.assertEquals(false, reader.has(NumberToken.class));
        Assert.assertEquals(false, reader.has(WordToken.class));
        Assert.assertEquals(false, reader.has(t -> true));
        Assert.assertEquals(false, reader.has(t -> false));
        Assert.assertEquals(false, reader.has("x", WordToken.class));

        Assert.assertNull(reader.getItem());

        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(""));
        Assert.assertEquals(false, reader.hasNext("a"));
        Assert.assertEquals(false, reader.hasNext("x"));
        Assert.assertEquals(false, reader.hasNext(NumberToken.class));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(false, reader.hasNext(t -> true));
        Assert.assertEquals(false, reader.hasNext(t -> false));
        Assert.assertEquals(false, reader.hasNext("x", WordToken.class));

        Assert.assertNull(reader.getItem());

        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(""));
        Assert.assertEquals(false, reader.hasPrevious("a"));
        Assert.assertEquals(false, reader.hasPrevious("x"));
        Assert.assertEquals(false, reader.hasPrevious(NumberToken.class));
        Assert.assertEquals(false, reader.hasPrevious(WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious(t -> true));
        Assert.assertEquals(false, reader.hasPrevious(t -> false));
        Assert.assertEquals(false, reader.hasPrevious("x", WordToken.class));

        Assert.assertNull(reader.getItem());

        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("")).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("a")).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("x")).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(NumberToken.class)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(WordToken.class)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(t -> true)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(t -> false)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("x", WordToken.class)).throwsException(TokenizeException.class);

        Assert.assertNull(reader.getItem());
    }

    private void testSingle() {
        Token token = new WordToken("a", 7);
        List<Token> list = new List<>(token);
        TokenReader reader = new TokenReader(list, TokenizeException::new);

        Assert.assertNotNull(reader.getItem());
        Assert.assertEquals(reader.getItem(), list.getFirstItem());
        Assert.assertEquals(reader.getItem().get(), token);
        Assert.assertThatCode(reader::readEnd).throwsException();

        Assert.assertEquals(true, reader.has());
        Assert.assertEquals(false, reader.has(""));
        Assert.assertEquals(true, reader.has("a"));
        Assert.assertEquals(false, reader.has("x"));
        Assert.assertEquals(false, reader.has(NumberToken.class));
        Assert.assertEquals(true, reader.has(WordToken.class));
        Assert.assertEquals(true, reader.has(Token.class));
        Assert.assertEquals(true, reader.has(t -> true));
        Assert.assertEquals(false, reader.has(t -> false));
        Assert.assertEquals(true, reader.has("a", WordToken.class));
        Assert.assertEquals(false, reader.has("b", WordToken.class));
        Assert.assertEquals(false, reader.has("a", CommentToken.class));

        Assert.assertEquals(reader.getItem(), list.getFirstItem());

        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(""));
        Assert.assertEquals(false, reader.hasNext("a"));
        Assert.assertEquals(false, reader.hasNext("x"));
        Assert.assertEquals(false, reader.hasNext(NumberToken.class));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(false, reader.hasNext(Token.class));
        Assert.assertEquals(false, reader.hasNext(t -> true));
        Assert.assertEquals(false, reader.hasNext(t -> false));
        Assert.assertEquals(false, reader.hasNext("a", WordToken.class));

        Assert.assertEquals(reader.getItem(), list.getFirstItem());

        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(""));
        Assert.assertEquals(false, reader.hasPrevious("a"));
        Assert.assertEquals(false, reader.hasPrevious("x"));
        Assert.assertEquals(false, reader.hasPrevious(NumberToken.class));
        Assert.assertEquals(false, reader.hasPrevious(WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious(Token.class));
        Assert.assertEquals(false, reader.hasPrevious(t -> true));
        Assert.assertEquals(false, reader.hasPrevious(t -> false));
        Assert.assertEquals(false, reader.hasPrevious("a", WordToken.class));

        Assert.assertEquals(reader.getItem(), list.getFirstItem());

        Assert.assertSame(token, reader.read());
        Assert.assertNull(reader.getItem());
        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertNull(reader.getItem());
        Assert.assertEquals(false, reader.hasPrevious()); // corner case
        Assert.assertEquals(false, reader.hasNext());
        reader.readEnd();
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertSame(token, reader.read("a"));
        Assert.assertNull(reader.getItem());
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertSame(token, reader.read(WordToken.class));
        Assert.assertNull(reader.getItem());
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertSame(token, reader.read(Token.class));
        Assert.assertNull(reader.getItem());
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertSame(token, reader.read(t -> true));
        Assert.assertNull(reader.getItem());
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertSame(token, reader.read("a", WordToken.class));
        Assert.assertNull(reader.getItem());
        reader.reset();

        Assert.assertEquals(list.getFirstItem(), reader.getItem());

        Assert.assertThatCode(() -> reader.read("")).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("x")).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(NumberToken.class)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read(t -> false)).throwsException(TokenizeException.class);
        Assert.assertThatCode(() -> reader.read("x", NumberToken.class)).throwsException(TokenizeException.class);

        Assert.assertEquals(list.getFirstItem(), reader.getItem());
    }

    private void testMultiple() {
        Token wordToken = new WordToken("a", -123);
        Token numberToken = new NumberToken("1", 1);
        Token emptyToken = new Token("", 333);
        List<Token> list = new List<>(wordToken, numberToken, emptyToken);
        TokenReader reader = new TokenReader(list, TokenizeException::new);

        Assert.assertEquals(list.getFirstItem(), reader.getItem());
        Assert.assertEquals(false, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious(""));
        Assert.assertEquals(false, reader.hasPrevious(Token.class));
        Assert.assertEquals(false, reader.hasPrevious(t -> true));
        Assert.assertEquals(true, reader.has());
        Assert.assertEquals(true, reader.has("a"));
        Assert.assertEquals(false, reader.has("b"));
        Assert.assertEquals(true, reader.has(WordToken.class));
        Assert.assertEquals(false, reader.has(NumberToken.class));
        Assert.assertEquals(true, reader.has(t -> t.getText().equals("a")));
        Assert.assertEquals(false, reader.has(t -> t.getText().equals("b")));
        Assert.assertEquals(true, reader.hasNext());
        Assert.assertEquals(true, reader.hasNext("1"));
        Assert.assertEquals(false, reader.hasNext("2"));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(true, reader.hasNext(NumberToken.class));
        Assert.assertEquals(true, reader.hasNext(t -> t.getText().equals("1")));
        Assert.assertEquals(false, reader.hasNext(t -> t.getText().equals("2")));
        Assert.assertEquals(true, reader.hasNext("1", NumberToken.class));
        Assert.assertEquals(false, reader.hasNext("0", NumberToken.class));
        Assert.assertEquals(false, reader.hasNext("1", CommentToken.class));

        Assert.assertThatCode(() -> reader.read("\n")).throwsException(TokenizeException.class);
        Assert.assertEquals(wordToken, reader.read());
        Assert.assertEquals(list.getFirstItem().getNextItem(), reader.getItem());
        Assert.assertEquals(true, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious("z"));
        Assert.assertEquals(true, reader.hasPrevious("a"));
        Assert.assertEquals(true, reader.hasPrevious(Token.class));
        Assert.assertEquals(true, reader.hasPrevious(WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious(NumberToken.class));
        Assert.assertEquals(true, reader.hasPrevious(t -> true));
        Assert.assertEquals(false, reader.hasPrevious(t -> false));
        Assert.assertEquals(true, reader.hasPrevious("a", WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious("b", WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious("a", CommentToken.class));
        Assert.assertEquals(true, reader.has());
        Assert.assertEquals(true, reader.has("1"));
        Assert.assertEquals(false, reader.has("2"));
        Assert.assertEquals(false, reader.has(WordToken.class));
        Assert.assertEquals(true, reader.has(NumberToken.class));
        Assert.assertEquals(true, reader.has(t -> t.getText().equals("1")));
        Assert.assertEquals(false, reader.has(t -> t.getText().equals("2")));
        Assert.assertEquals(true, reader.hasNext());
        Assert.assertEquals(true, reader.hasNext(""));
        Assert.assertEquals(false, reader.hasNext("\n"));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(true, reader.hasNext(Token.class));
        Assert.assertEquals(true, reader.hasNext(t -> t.getText().equals("")));
        Assert.assertEquals(false, reader.hasNext(t -> t.getText().equals("\n")));

        Assert.assertThatCode(() -> reader.read("a")).throwsException(TokenizeException.class);
        Assert.assertEquals(numberToken, reader.read());
        Assert.assertEquals(list.getFirstItem().getNextItem().getNextItem(), reader.getItem());
        Assert.assertEquals(true, reader.hasPrevious());
        Assert.assertEquals(false, reader.hasPrevious("0"));
        Assert.assertEquals(true, reader.hasPrevious("1"));
        Assert.assertEquals(true, reader.hasPrevious(Token.class));
        Assert.assertEquals(false, reader.hasPrevious(WordToken.class));
        Assert.assertEquals(true, reader.hasPrevious(NumberToken.class));
        Assert.assertEquals(true, reader.hasPrevious(t -> true));
        Assert.assertEquals(false, reader.hasPrevious(t -> false));
        Assert.assertEquals(true, reader.has());
        Assert.assertEquals(true, reader.has(""));
        Assert.assertEquals(false, reader.has("x"));
        Assert.assertEquals(false, reader.has(WordToken.class));
        Assert.assertEquals(true, reader.has(Token.class));
        Assert.assertEquals(true, reader.has(t -> t.getText().equals("")));
        Assert.assertEquals(false, reader.has(t -> t.getText().equals("?")));
        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext("?"));
        Assert.assertEquals(false, reader.hasNext(""));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(false, reader.hasNext(Token.class));
        Assert.assertEquals(false, reader.hasNext(t -> true));
        Assert.assertEquals(false, reader.hasNext(t -> false));

        Assert.assertThatCode(() -> reader.read("1")).throwsException(TokenizeException.class);
        Assert.assertEquals(emptyToken, reader.read());
        Assert.assertNull(reader.getItem());
        Assert.assertEquals(false, reader.hasPrevious()); // corner case
        Assert.assertEquals(false, reader.hasPrevious("")); // corner case
        Assert.assertEquals(false, reader.hasPrevious("x"));
        Assert.assertEquals(false, reader.hasPrevious(Token.class)); // corner case
        Assert.assertEquals(false, reader.hasPrevious(WordToken.class));
        Assert.assertEquals(false, reader.hasPrevious(NumberToken.class));
        Assert.assertEquals(false, reader.hasPrevious(t -> true)); // corner case
        Assert.assertEquals(false, reader.hasPrevious(t -> false));
        Assert.assertEquals(false, reader.has());
        Assert.assertEquals(false, reader.has(""));
        Assert.assertEquals(false, reader.has("a"));
        Assert.assertEquals(false, reader.has(WordToken.class));
        Assert.assertEquals(false, reader.has(Token.class));
        Assert.assertEquals(false, reader.has(t -> true));
        Assert.assertEquals(false, reader.has(t -> false));
        Assert.assertEquals(false, reader.hasNext());
        Assert.assertEquals(false, reader.hasNext(""));
        Assert.assertEquals(false, reader.hasNext("a"));
        Assert.assertEquals(false, reader.hasNext(WordToken.class));
        Assert.assertEquals(false, reader.hasNext(Token.class));
        Assert.assertEquals(false, reader.hasNext(t -> true));
        Assert.assertEquals(false, reader.hasNext(t -> false));

        Assert.assertThatCode(reader::read).throwsException(TokenizeException.class);
        Assert.assertNull(reader.getItem());

        reader.reset();

        Assert.assertEquals(wordToken, reader.read("a"));
        Assert.assertEquals(numberToken, reader.read("1"));
        Assert.assertEquals(emptyToken, reader.read(""));

        reader.reset();

        Assert.assertEquals(wordToken, reader.read(WordToken.class));
        Assert.assertEquals(numberToken, reader.read(NumberToken.class));
        Assert.assertEquals(emptyToken, reader.read(Token.class));

        reader.reset();

        Assert.assertEquals(wordToken, reader.read(t -> true));
        Assert.assertEquals(numberToken, reader.read(t -> true));
        Assert.assertEquals(emptyToken, reader.read(t -> true));

        reader.reset();

        Assert.assertEquals(wordToken, reader.read("a", WordToken.class));
        Assert.assertEquals(numberToken, reader.read("1", NumberToken.class));
        Assert.assertEquals(emptyToken, reader.read("", Token.class));

        Assert.assertEquals(
            333, Assert.assertThatCode(reader::read).throwsException(TokenizeException.class).getPosition()
        );

        reader.reset();

        Assert.assertEquals(
            -123, Assert.assertThatCode(() -> reader.read("?")).throwsException(TokenizeException.class).getPosition()
        );
    }

    private void testSkip() {
        WordToken wordToken = new WordToken("a", 2);
        TokenReader reader = new TokenReader(new List<>(
            new WhitespaceToken("\t", 0), new WhitespaceToken(" ", 1), wordToken
        ), TokenizeException::new);
        reader.skip(WhitespaceToken.class);
        Assert.assertSame(wordToken, reader.read());
    }
}
