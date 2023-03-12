package cz.mg.tokenizer;

import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.test.Performance;
import cz.mg.tokenizer.services.parsers.NameTokenParser;
import cz.mg.tokenizer.services.parsers.OptimizedNameTokenParser;

import java.util.Random;

public @Test class TempPerformanceTest {
    private static final int ITERATIONS = 25;

    public static void main(String[] args) {
        System.out.println("Running " + TempPerformanceTest.class.getSimpleName() + " ... ");

        TempPerformanceTest test = new TempPerformanceTest();
        test.testPerformance();
    }

    private void testPerformance() {
        int count = 10000000;

        long oldTime = 0;
        long newTime = 0;

        NameTokenParser oldParser = NameTokenParser.getInstance();
        OptimizedNameTokenParser newParser = OptimizedNameTokenParser.getInstance();

        for (int i = 0; i < ITERATIONS; i++) {
            char[] array = generate(count, i);
            long[] oldCount = new long[1];
            long[] newCount = new long[1];

            oldTime += Performance.measure(() -> {
                for (char ch : array) {
                    oldCount[0] += oldParser.nameOrNumber(ch) ? 1 : 0;
                }
            });

            newTime += Performance.measure(() -> {
                for (char ch : array) {
                    newCount[0] += newParser.nameOrNumber(ch) ? 1 : 0;
                }
            });

            Assert.assertEquals(oldCount[0], newCount[0]);
        }

        System.out.println();
        System.out.println("##### " + count + " #####");
        System.out.println("Average old time: " + oldTime / ITERATIONS);
        System.out.println("Average new time: " + newTime / ITERATIONS);
    }

    private char[] generate(int count, int seed) {
        Random random = new Random(seed);
        char[] array = new char[count];
        for (int i = 0; i < count; i++) {
            array[i] = (char) random.nextInt(128);
        }
        return array;
    }
}
