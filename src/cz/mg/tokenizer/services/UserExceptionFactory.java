package cz.mg.tokenizer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.tokenizer.entities.Position;
import cz.mg.tokenizer.exceptions.TraceableException;
import cz.mg.tokenizer.exceptions.UserException;

import java.nio.file.Path;

public class UserExceptionFactory {
    private static volatile @Service UserExceptionFactory instance;

    public static @Service UserExceptionFactory getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new UserExceptionFactory();
                    instance.positionService = PositionService.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service PositionService positionService;

    private UserExceptionFactory() {
    }

    public @Mandatory UserException create(
        @Mandatory Path path,
        @Mandatory String content,
        @Mandatory TraceableException exception
    ) {
        return new UserException(
            exception.getPosition(),
            getMessage(path, content, exception),
            exception
        );
    }

    private @Mandatory String getMessage(
        @Mandatory Path path,
        @Mandatory String content,
        @Mandatory TraceableException exception
    ) {
        Position position = positionService.find(content, exception.getPosition());
        return "In file '" + path +
            "' at line " + position.getRow() +
            " column " + position.getColumn() + ": " +
            exception.getMessage();
    }
}
