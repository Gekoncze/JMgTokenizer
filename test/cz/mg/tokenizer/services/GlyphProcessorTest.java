package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.tokenizer.services.processors.GlyphProcessor;

import java.util.Arrays;
import java.util.Iterator;

public @Test class GlyphProcessorTest {
    public static void main(String[] args) {
        System.out.print("Running " + GlyphProcessorTest.class.getSimpleName() + " ... ");

        GlyphProcessorTest test = new GlyphProcessorTest();
        test.testProcessing();

        System.out.println("OK");
    }

    private void testProcessing() {
        testProcessing("");
        testProcessing(" ", new Glyph(' ', 0));
        testProcessing("\n", new Glyph('\n', 0));
        testProcessing("\t", new Glyph('\t', 0));
        testProcessing(";", new Glyph(';', 0));
        testProcessing("a", new Glyph('a', 0));
        testProcessing("a ", new Glyph('a', 0), new Glyph(' ', 1));
        testProcessing(" a", new Glyph(' ', 0), new Glyph('a', 1));
        testProcessing("a\n1", new Glyph('a', 0), new Glyph('\n', 1), new Glyph('1', 2));
        testProcessing("\t \n", new Glyph('\t', 0), new Glyph(' ', 1), new Glyph('\n', 2));
        testProcessing("\t \r\n", new Glyph('\t', 0), new Glyph(' ', 1), new Glyph('\r', 2), new Glyph('\n', 3));
    }

    private void testProcessing(String content, Glyph... expectedGlyphs) {
        GlyphProcessor processor = GlyphProcessor.getInstance();
        List<Glyph> actualGlyphs = processor.process(content);

        Assert.assertEquals(Arrays.stream(expectedGlyphs).count(), actualGlyphs.count());

        Iterator<Glyph> expectedGlyphsIterator = Arrays.stream(expectedGlyphs).iterator();
        Iterator<Glyph> actualGlyphsIterator = actualGlyphs.iterator();
        while (expectedGlyphsIterator.hasNext() && actualGlyphsIterator.hasNext()) {
            Glyph expectedGlyph = expectedGlyphsIterator.next();
            Glyph actualGlyph = actualGlyphsIterator.next();
            Assert.assertEquals(expectedGlyph.getCharacter(), actualGlyph.getCharacter());
            Assert.assertEquals(expectedGlyph.getPosition(), actualGlyph.getPosition());
        }
    }
}
