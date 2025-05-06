package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.test.Assert;
import cz.mg.tokenizer.exceptions.TraceableException;
import cz.mg.tokenizer.exceptions.UserException;

import java.nio.file.Path;

public @Test class UserExceptionFactoryTest {
    private static final Path PATH = Path.of("/foo/bar");

    public static void main(String[] args) {
        System.out.print("Running " + UserExceptionFactoryTest.class.getSimpleName() + " ... ");

        UserExceptionFactoryTest test = new UserExceptionFactoryTest();
        test.testCreateForEmptyFile();
        test.testCreateForFile();

        System.out.println("OK");
    }

    private final @Service UserExceptionFactory factory = UserExceptionFactory.getInstance();

    private void testCreateForEmptyFile() {
        UserException exception = factory.create(PATH, "", new TraceableException(0, "Test error."));
        Assert.assertEquals(0, exception.getPosition());
        Assert.assertEquals("In file '/foo/bar' at line 1 column 1: Test error.", exception.getMessage());
    }

    private void testCreateForFile() {
        UserException exception = factory.create(
            PATH,
            "package foo.bar;\n\nimport foo.bra.FooBar;",
            new TraceableException(29, "Missing package bra.")
        );
        Assert.assertEquals(29, exception.getPosition());
        Assert.assertEquals("In file '/foo/bar' at line 3 column 12: Missing package bra.", exception.getMessage());
    }
}
