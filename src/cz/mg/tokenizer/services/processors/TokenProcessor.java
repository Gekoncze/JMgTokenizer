package cz.mg.tokenizer.services.processors;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.utilities.TokenBuilder;
import cz.mg.tokenizer.utilities.TokenizeException;

@Deprecated(forRemoval = true) // TODO
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
     */
//    public @Mandatory List<List<Token>> process(@Mandatory List<List<Glyph>> rows) {
//        List<List<Token>> lines = new List<>();
//        boolean multiLineComment = false;
//
//        for (List<Glyph> row : rows) {
//            List<Token> line = new List<>();
//            TokenBuilder builder = null;
//            char pch = '\0';
//
//            for (ListItem<Glyph> glyphItem = row.getFirstItem(); glyphItem != null; glyphItem = glyphItem.getNextItem()) {
//                char ch = glyphItem.get().getCharacter();
//                int i = glyphItem.get().getPosition();
//
//                if (multiLineComment) {
//                    if (isMultiLineCommentEnd(pch, ch)) {
//                        multiLineComment = false;
//                    }
//                } else if (type != null) {
//                    if (isCharacter(type)) {
//                        if (!isBackslash(pch) && isSingleQuote(ch)) {
//                            line.addLast(create(type, text, start));
//                            type = null;
//                            text = null;
//                            start = null;
//                        } else {
//                            text.append(ch);
//                        }
//                    } else if (isString(type)) {
//                        if (!isBackslash(pch) && isDoubleQuote(ch)) {
//                            line.addLast(create(type, text, start));
//                            type = null;
//                            text = null;
//                            start = null;
//                        } else {
//                            text.append(ch);
//                        }
//                    } else if (isName(type)) {
//                        if (!(isUppercase(ch) || isLowercase(ch) || isNumber(ch) || isUnderscore(ch))) {
//                            line.addLast(create(type, text, start));
//                            type = null;
//                            text = null;
//                            start = null;
//                            glyphItem = glyphItem.getPreviousItem();
//                            ch = '\0';
//                        } else {
//                            text.append(ch);
//                        }
//                    } else if (isNumber(type)) {
//                        if (!(isNumber(ch) || isDot(ch) || isUppercase(ch) || isLowercase(ch))) {
//                            line.addLast(create(type, text, start));
//                            type = null;
//                            text = null;
//                            start = null;
//                            glyphItem = glyphItem.getPreviousItem();
//                            ch = '\0';
//                        } else {
//                            text.append(ch);
//                        }
//                    } else if (isSpecial(type)) {
//                        if (!isSpecial(ch)) {
//                            line.addLast(create(type, text, start));
//                            type = null;
//                            text = null;
//                            start = null;
//                            glyphItem = glyphItem.getPreviousItem();
//                            ch = '\0';
//                        } else {
//                            text.append(ch);
//                        }
//                    } else {
//                        throw new IllegalStateException("Unexpected token type " + type + "during parsing.");
//                    }
//                } else {
//                    if (isSingleLineComment(pch, ch)) {
//                        line.removeLast();
//                        break;
//                    } else if (isMultiLineCommentBegin(pch, ch)) {
//                        multiLineComment = true;
//                        line.removeLast();
//                    } else if (isWhitespace(ch)) {
//                        // whitespaces are skipped
//                    } else if (isSingleQuote(ch)) {
//                        type = TokenType.CHARACTER;
//                        text = new StringBuilder();
//                        start = i;
//                    } else if (isDoubleQuote(ch)) {
//                        type = TokenType.STRING;
//                        text = new StringBuilder();
//                        start = i;
//                    } else if (isUppercase(ch) || isLowercase(ch) || isUnderscore(ch)) {
//                        type = TokenType.NAME;
//                        text = new StringBuilder("" + ch);
//                        start = i;
//                    } else if (isNumber(ch)) {
//                        type = TokenType.NUMBER;
//                        text = new StringBuilder("" + ch);
//                        start = i;
//                    } else if (isBracket(ch)) {
//                        line.addLast(create(TokenType.SPECIAL, "" + ch, i));
//                    } else if (isSpecial(ch)) {
//                        type = TokenType.SPECIAL;
//                        text = new StringBuilder("" + ch);
//                        start = i;
//                    } else {
//                        throw new TokenizeException(i, "Unknown character '" + ch + "'.");
//                    }
//                }
//
//                pch = ch;
//            }
//
//            if (type == TokenType.NAME || type == TokenType.NUMBER || type == TokenType.SPECIAL) {
//                line.addLast(create(type, text, start));
//            }
//
//            if (type == TokenType.CHARACTER || type == TokenType.STRING) {
//                throw new TokenizeException(start, "Missing right quote.");
//            }
//
//            lines.addLast(line);
//        }
//        return lines;
//    }

    private boolean isSingleLineComment(char pch, char ch) {
        return pch == '/' && ch == '/';
    }

    private boolean isMultiLineCommentBegin(char pch, char ch) {
        return pch == '/' && ch == '*';
    }

    private boolean isMultiLineCommentEnd(char pch, char ch) {
        return pch == '*' && ch == '/';
    }
}
