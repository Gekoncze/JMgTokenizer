package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Test;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;
import cz.mg.collections.services.CollectionComparator;
import cz.mg.collections.services.StringJoiner;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.tokenizer.services.processors.NewlineProcessor;

public @Test class NewlineProcessorTest {
    public static void main(String[] args) {
        System.out.print("Running " + NewlineProcessorTest.class.getSimpleName() + " ... ");

        NewlineProcessorTest test = new NewlineProcessorTest();
        test.testProcessing();

        System.out.println("OK");
    }

    private void testProcessing() {
        testProcessing(
            new List<>(new Glyph('a', 0)),
            new List<List<Glyph>>(
                new List<>(new Glyph('a', 0))
            )
        );

        testProcessing(
            new List<>(new Glyph('\n', 0)),
            new List<>(
                new List<>(),
                new List<>()
            )
        );

        testProcessing(
            new List<>(new Glyph('a', 0), new Glyph('b', 1), new Glyph('\n', 2), new Glyph('c', 3)),
            new List<>(
                new List<>(new Glyph('a', 0), new Glyph('b', 1)),
                new List<>(new Glyph('c', 3))
            )
        );

        testProcessing(
            new List<>(new Glyph('\n', 0), new Glyph('a', 1), new Glyph('b', 2), new Glyph('c', 3)),
            new List<>(
                new List<>(),
                new List<>(new Glyph('a', 1), new Glyph('b', 2), new Glyph('c', 3))
            )
        );

        testProcessing(
            new List<>(new Glyph('a', 0), new Glyph('b', 1), new Glyph('c', 2), new Glyph('\n', 3)),
            new List<>(
                new List<>(new Glyph('a', 0), new Glyph('b', 1), new Glyph('c', 2)),
                new List<>()
            )
        );

        testProcessing(
            new List<>(new Glyph('a', 0), new Glyph('\n', 1), new Glyph('\n', 2), new Glyph('b', 3)),
            new List<>(
                new List<>(new Glyph('a', 0)),
                new List<>(),
                new List<>(new Glyph('b', 3))
            )
        );

        testProcessing(
            new List<>(new Glyph('a', 0), new Glyph('\n', 1), new Glyph('b', 2), new Glyph('\n', 3), new Glyph('c', 4)),
            new List<>(
                new List<>(new Glyph('a', 0)),
                new List<>(new Glyph('b', 2)),
                new List<>(new Glyph('c', 4))
            )
        );
    }

    private void testProcessing(@Mandatory List<Glyph> glyphs, @Mandatory List<List<Glyph>> expectedLines) {
        NewlineProcessor processor = NewlineProcessor.getInstance();
        CollectionComparator comparator = CollectionComparator.getInstance();

        List<List<Glyph>> actualLines = processor.process(glyphs);

        Assert.assertEquals(expectedLines.count(), actualLines.count());
        Assert.assertEquals(
            expectedLines,
            actualLines,
            (expectedGlyphs, actualGlyphs) -> comparator.equals(
                expectedGlyphs,
                actualGlyphs,
                (e, a) -> e.getCharacter() == a.getCharacter() && e.getPosition() == a.getPosition()
            ),
            line -> StringJoiner.getInstance().join(
                line, " ", glyph -> "(" + glyph.getCharacter() + "," + glyph.getPosition() + ")"
            )
        );
    }
}
