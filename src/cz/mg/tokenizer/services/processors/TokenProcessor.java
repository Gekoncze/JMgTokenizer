package cz.mg.tokenizer.services.processors;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.TokenType;
import cz.mg.tokenizer.entities.Glyph;
import cz.mg.tokenizer.utilities.TokenizeException;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

import java.util.Objects;

public @Service class TokenProcessor {
    private static @Optional TokenProcessor instance;

    public static @Mandatory TokenProcessor getInstance() {
        if (instance == null) {
            instance = new TokenProcessor();
        }
        return instance;
    }

    private TokenProcessor() {
    }

    /**
     * Groups characters into tokens.
     * Comments are removed.
     */
    public @Mandatory List<List<Token>> process(@Mandatory List<List<Glyph>> rows) {
        List<List<Token>> lines = new List<>();
        boolean multiLineComment = false;

        for (List<Glyph> row : rows) {
            List<Token> line = new List<>();
            Integer start = null;
            TokenType type = null;
            StringBuilder text = null;
            char pch = '\0';

            for (ListItem<Glyph> glyphItem = row.getFirstItem(); glyphItem != null; glyphItem = glyphItem.getNextItem()) {
                char ch = glyphItem.get().getCharacter();
                int i = glyphItem.get().getPosition();

                if (multiLineComment) {
                    if (isMultiLineCommentEnd(pch, ch)) {
                        multiLineComment = false;
                    }
                } else if (type != null) {
                    if (isCharacter(type)) {
                        if (!isBackslash(pch) && isSingleQuote(ch)) {
                            line.addLast(create(type, text.toString(), start));
                            type = null;
                            text = null;
                            start = null;
                        } else {
                            text.append(ch);
                        }
                    } else if (isString(type)) {
                        if (!isBackslash(pch) && isDoubleQuote(ch)) {
                            line.addLast(create(type, text.toString(), start));
                            type = null;
                            text = null;
                            start = null;
                        } else {
                            text.append(ch);
                        }
                    } else if (isName(type)) {
                        if (!(isUppercase(ch) || isLowercase(ch) || isNumber(ch) || isUnderscore(ch))) {
                            line.addLast(create(type, text.toString(), start));
                            type = null;
                            text = null;
                            start = null;
                            glyphItem = glyphItem.getPreviousItem();
                            ch = '\0';
                        } else {
                            text.append(ch);
                        }
                    } else if (isNumber(type)) {
                        if (!(isNumber(ch) || isDot(ch) || isUppercase(ch) || isLowercase(ch))) {
                            line.addLast(create(type, text.toString(), start));
                            type = null;
                            text = null;
                            start = null;
                            glyphItem = glyphItem.getPreviousItem();
                            ch = '\0';
                        } else {
                            text.append(ch);
                        }
                    } else if (isSpecial(type)) {
                        if (!isSpecial(ch)) {
                            line.addLast(create(type, text.toString(), start));
                            type = null;
                            text = null;
                            start = null;
                            glyphItem = glyphItem.getPreviousItem();
                            ch = '\0';
                        } else {
                            text.append(ch);
                        }
                    } else {
                        throw new IllegalStateException("Unexpected token type " + type + "during parsing.");
                    }
                } else {
                    if (isSingleLineComment(pch, ch)) {
                        line.removeLast();
                        break;
                    } else if (isMultiLineCommentBegin(pch, ch)) {
                        multiLineComment = true;
                        line.removeLast();
                    } else if (isWhitespace(ch)) {
                        // whitespaces are skipped
                    } else if (isSingleQuote(ch)) {
                        type = TokenType.CHARACTER;
                        text = new StringBuilder();
                        start = i;
                    } else if (isDoubleQuote(ch)) {
                        type = TokenType.STRING;
                        text = new StringBuilder();
                        start = i;
                    } else if (isUppercase(ch) || isLowercase(ch) || isUnderscore(ch)) {
                        type = TokenType.NAME;
                        text = new StringBuilder("" + ch);
                        start = i;
                    } else if (isNumber(ch)) {
                        type = TokenType.NUMBER;
                        text = new StringBuilder("" + ch);
                        start = i;
                    } else if (isBracket(ch)) {
                        line.addLast(create(TokenType.SPECIAL, "" + ch, i));
                    } else if (isSpecial(ch)) {
                        type = TokenType.SPECIAL;
                        text = new StringBuilder("" + ch);
                        start = i;
                    } else {
                        throw new TokenizeException(i, "Unknown character '" + ch + "'.");
                    }
                }

                pch = ch;
            }

            if (type == TokenType.NAME || type == TokenType.NUMBER) {
                line.addLast(create(type, text.toString(), start));
            }

            if (type == TokenType.CHARACTER || type == TokenType.STRING) {
                throw new TokenizeException(start, "Missing right quote.");
            }

            lines.addLast(line);
        }
        return lines;
    }

    private boolean isCharacter(@Mandatory TokenType type) {
        return type == TokenType.CHARACTER;
    }

    private boolean isString(@Mandatory TokenType type) {
        return type == TokenType.STRING;
    }

    private boolean isName(@Mandatory TokenType type) {
        return type == TokenType.NAME;
    }

    private boolean isNumber(@Mandatory TokenType type) {
        return type == TokenType.NUMBER;
    }

    private boolean isSpecial(@Mandatory TokenType type) {
        return type == TokenType.SPECIAL;
    }

    private boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\n';
    }

    private boolean isUppercase(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private boolean isLowercase(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isSingleQuote(char ch) {
        return ch == '\'';
    }

    private boolean isDoubleQuote(char ch) {
        return ch == '"';
    }

    private boolean isBackslash(char ch) {
        return ch == '\\';
    }

    private boolean isUnderscore(char ch) {
        return ch == '_';
    }

    private boolean isDot(char ch) {
        return ch == '.';
    }

    private boolean isSingleLineComment(char pch, char ch) {
        return pch == '/' && ch == '/';
    }

    private boolean isMultiLineCommentBegin(char pch, char ch) {
        return pch == '/' && ch == '*';
    }

    private boolean isMultiLineCommentEnd(char pch, char ch) {
        return pch == '*' && ch == '/';
    }

    private boolean isBracket(char ch) {
        return ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}';
    }

    private boolean isSpecial(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '<' || ch == '>' || ch == '=' || ch == '~' ||
            ch == '!' || ch == '&' || ch == '|' || ch == '%' || ch == '#' || ch == '?' || ch == ':' || ch == '.' ||
            ch == ',' || ch == '@' || ch == '$' || ch == '^' || ch == ';';
    }

    private @Mandatory Token create(@Mandatory TokenType type, @Mandatory String text, @Mandatory Integer position) {
        return new Token(
            Objects.requireNonNull(type),
            Objects.requireNonNull(text),
            Objects.requireNonNull(position)
        );
    }
}
