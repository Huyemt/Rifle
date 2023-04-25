package org.bullet.compiler.lexer;

import org.bullet.exceptions.common.FileCorruptingExceiption;
import org.bullet.exceptions.common.ParsingException;
import org.huyemt.crypto4j.Crypto4J;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Huyemt
 */

public class Lexer implements ILexer {

    public Position position;
    public Token currentToken;
    public Position peekPosition;
    public Token currentPeekToken;
    public static final HashMap<String, TokenKind> tokens = new HashMap<>();
    public static final HashMap<String, TokenKind> keywords = new HashMap<>();

    static {
        tokens.put("\0", TokenKind.EOF);
        tokens.put("+", TokenKind.PLUS);
        tokens.put("-", TokenKind.MINUS);
        tokens.put("*", TokenKind.STAR);
        tokens.put("/", TokenKind.SLASH);
        tokens.put("(", TokenKind.SLPAREN);
        tokens.put(")", TokenKind.SRPAREN);
        tokens.put("[", TokenKind.MLPAREN);
        tokens.put("]", TokenKind.MRPAREN);
        tokens.put("{", TokenKind.BLPAREN);
        tokens.put("}", TokenKind.BRPAREN);
        tokens.put(",", TokenKind.COMMA);
        tokens.put(";", TokenKind.SEMICOLON);
        tokens.put("=", TokenKind.ASSIGN);
        tokens.put("!", TokenKind.EXCLAMATION);
        tokens.put("&", TokenKind.L_AND);
        tokens.put("|", TokenKind.L_OR);
        tokens.put(">", TokenKind.GREATER);
        tokens.put("<", TokenKind.LESSER);
        tokens.put(".", TokenKind.POINT);
        tokens.put("@", TokenKind.AT);
        tokens.put("#", TokenKind.SHARP);
        tokens.put(":", TokenKind.COLON);

        keywords.put("true", TokenKind.TRUE);
        keywords.put("false", TokenKind.FALSE);
        keywords.put("var", TokenKind.VAR);
        keywords.put("if", TokenKind.IF);
        keywords.put("else", TokenKind.ELSE);
        keywords.put("and", TokenKind.AND);
        keywords.put("or", TokenKind.OR);
        keywords.put("not", TokenKind.NOT);
        keywords.put("while", TokenKind.WHILE);
        keywords.put("for", TokenKind.FOR);
        keywords.put("until", TokenKind.UNTIL);
        keywords.put("function", TokenKind.FUNCTION);
        keywords.put("return", TokenKind.RETURN);
        keywords.put("break", TokenKind.BREAK);
        keywords.put("continue", TokenKind.CONTINUE);
    }

    public Lexer(String source) throws ParsingException {
        position = new Position(source, null);
        peekPosition = new Position(source, null);

        this.next();
    }

    public Lexer(File source) throws FileCorruptingExceiption, ParsingException {
        String s;
        try {
            s = Utils.readFile(source);
        } catch (IOException e) {
            throw new FileCorruptingExceiption("This file is corrupted");
        }

        position = new Position(s, source.getAbsolutePath());
        peekPosition = new Position(s, source.getAbsolutePath());

        this.next();
    }


    @Override
    public void next() throws ParsingException {
        while (Character.isWhitespace(position.currentChar) || position.currentChar == '/') {
            if (position.currentChar == '\n') {
                position.y++;
                position.x = 1;
                position.lineX = position.index + 1;
            } else if (position.currentChar == '/') {
                // 单行注释
                if (peekChar(1) == '/') {
                    position.next();

                    while (position.currentChar != '\n') {
                        if (position.currentChar == '\0') break;
                        position.next();
                    }

                    continue;
                }

                // 多行注释
                if (peekChar(1) == '*') {
                    Position p = new Position(position.source, position.path);
                    p.currentChar = '*';
                    p.index = position.index + 1;
                    p.x = position.x + 1;
                    p.y = position.y;
                    p.lineX = position.lineX;

                    position.next(2);

                    boolean flag = false;

                    while (position.currentChar != '\0') {
                        if (position.currentChar == '*' && peekChar(1) == '/') {
                            position.next(2);
                            flag = true;
                            break;
                        }

                        if (position.currentChar == '\n') {
                            position.y++;
                            position.x = 1;
                            position.lineX = position.index + 1;
                        }

                        position.next();
                    }

                    if (!flag) {
                        throw new ParsingException(p, "Maybe you missed the '*/'");
                    }

                    continue;
                }

                break;
            }

            position.next();
        }

        if (tokens.containsKey(position.currentChar.toString())) {
            switch (position.currentChar) {
                case '=':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.EQUAL);
                        break;
                    }
                case '!':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.NOT_EQUAL);
                        break;
                    }
                case '>':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.GREATER_OR_EQUAL);
                        break;
                    }
                case '<':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.LESSER_OR_EQUAL);
                        break;
                    }
                case '&':
                    if (this.peekChar(1) == '&') {
                        position.next();
                        this.makeToken(TokenKind.AND);
                        break;
                    }
                case '|':
                    if (this.peekChar(1) == '|') {
                        position.next();
                        this.makeToken(TokenKind.OR);
                        break;
                    }
                case '+':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.ASSIGN_ADD);
                        break;
                    }
                case '-':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.ASSIGN_SUB);
                        break;
                    }
                case '*':
                    if (this.peekChar(1) == '*') {
                        position.next();

                        if (this.peekChar(1) == '=') {
                            position.next();
                            this.makeToken(TokenKind.ASSIGN_POW);
                            break;
                        }

                        this.makeToken(TokenKind.POW);
                        break;
                    }
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.ASSIGN_MUL);
                        break;
                    }
                case '/':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.ASSIGN_DIV);
                        break;
                    }
                case ':':
                    if (this.peekChar(1) == '=') {
                        position.next();
                        this.makeToken(TokenKind.C_ASSIGN);
                        break;
                    }
                default:
                    this.makeToken(tokens.get(position.currentChar.toString()));
                    break;
            }

            position.next();
            return;
        }

        if (Character.isDigit(position.currentChar)) {
            this.makeNumber();
            return;
        }

        if (Character.isLetter(position.currentChar)) {
            this.makeIdentifier();
            return;
        }

        if (position.currentChar == '\"') {
            this.makeString();
            return;
        }

        throw new ParsingException(position, String.format("Current char '%c' is illegal", position.currentChar));
    }

    public void next(int times) throws ParsingException {
        for (int i = 0; i < times; i++) {
            this.next();
        }
    }

    @Override
    public void makeToken(TokenKind kind) {
        currentToken = new Token(kind, position);
    }

    @Override
    public void makeToken(TokenKind kind, String value) {
        currentToken = new VToken(kind, position, value);
    }

    @Override
    public void expectToken(TokenKind kind) throws ParsingException {
        if (currentToken.kind == kind)
            this.next();
        else
            throw new ParsingException(position, String.format("\"%s\" expected", kind));
    }

    @Override
    public void checkToken(TokenKind kind) throws ParsingException {
        if (currentToken.kind != kind) {
            throw new ParsingException(position, String.format("need \"%s\"", kind));
        }
    }

    @Override
    public char peekChar(int distance) {
        int index = position.index + distance;

        return index < position.source.length() ? position.source.charAt(index) : '\0';
    }

    @Override
    public void beginPeek() {
        currentPeekToken = currentToken;
        peekPosition.index = position.index;
        peekPosition.x = position.x;
        peekPosition.y = position.y;
        peekPosition.lineX = position.lineX;
        peekPosition.currentChar = position.currentChar;
    }

    @Override
    public void endPeek() {
        currentToken = currentPeekToken;
        position.index = peekPosition.index;
        position.x = peekPosition.x;
        position.y = peekPosition.y;
        position.lineX = peekPosition.lineX;
        position.currentChar = peekPosition.currentChar;
    }

    private void makeNumber() throws ParsingException {
        int start = position.index;
        boolean flag = false;

        while (Character.isDigit(position.currentChar) || position.currentChar.equals('.')) {
            if (position.currentChar.equals('.')) {
                if (flag) {
                    throw new ParsingException(position, "Illegal numerical constant");
                }

                flag = true;
            }

            position.next();
        }

        if (Character.isLetter(position.currentChar)) {
            throw new ParsingException(position, "Identifiers cannot start with a number");
        }

        this.makeToken(TokenKind.VT_NUMBER, position.source.substring(start, position.index));
    }

    private void makeIdentifier() {
        int start = position.index;

        while (Character.isLetterOrDigit(position.currentChar) || position.currentChar == '_') {
            position.next();
        }

        String identifier = position.source.substring(start, position.index);

        if (keywords.containsKey(identifier)) {
            this.makeToken(keywords.get(identifier));
            return;
        }

        this.makeToken(TokenKind.IDENTIFIER, identifier);
    }

    private void makeString() throws ParsingException {
        position.next();

        StringBuilder builder = new StringBuilder();

        while (position.currentChar != '\"') {
            if (position.currentChar == '\\') {
                char c = this.peekChar(1);
                if (c == 'b') {
                    builder.append('\b');
                    position.next();
                } else if (c == 'n') {
                    builder.append('\n');
                    position.next();
                } else if (c == 't') {
                    builder.append('\t');
                    position.next();
                } else if (c == 'r') {
                    builder.append('\r');
                    position.next();
                } else if (c == '\'') {
                    builder.append('\'');
                    position.next();
                } else if (c == '\"') {
                    builder.append('\"');
                    position.next();
                } else if (c == '\\') {
                    builder.append('\\');
                    position.next();
                } else if (c == 'u') {
                    position.next();

                    for (int i = 1; i <= 4; i++) {
                        if (!Character.isLetterOrDigit(peekChar(i))) {
                            throw new ParsingException(position, "Wrong unicode format");
                        }
                    }

                    position.next();
                    int start = position.index;

                    for (int i = 0; i < 3; i++) {
                        position.next();
                    }

                    builder.append(Crypto4J.Unicode.decrypt("\\u".concat(position.source.substring(start, position.index + 1))));
                } else builder.append(position.currentChar);
            } else {
                builder.append(position.currentChar);
            }

            position.next();
        }

        position.next();

        this.makeToken(TokenKind.VT_STRING, builder.toString());
    }
}
