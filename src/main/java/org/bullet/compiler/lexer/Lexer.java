package org.bullet.compiler.lexer;

import org.bullet.exceptions.FileCorruptingExceiption;
import org.bullet.exceptions.ParsingException;
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
    }

    public Lexer(String source) throws ParsingException {
        position = new Position(source);
        peekPosition = new Position(source);

        this.next();
    }

    public Lexer(File source) throws FileCorruptingExceiption, ParsingException {
        String s;
        try {
            s = Utils.readFile(source);
        } catch (IOException e) {
            throw new FileCorruptingExceiption("This file is corrupted");
        }

        position = new Position(s);

        this.next();
    }


    @Override
    public void next() throws ParsingException {
        while (Character.isWhitespace(position.currentChar)) {
            if (position.currentChar == '\n') {
                position.y++;
                position.x = 1;
                position.lineX = position.index + 1;
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
                case '*':
                    if (this.peekChar(1) == '*') {
                        position.next();
                        this.makeToken(TokenKind.POW);
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

        throw new ParsingException(position, String.format("Current char '%c' is illegal", position.currentChar));
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

        while (Character.isLetterOrDigit(position.currentChar)) {
            position.next();
        }

        String identifier = position.source.substring(start, position.index);

        if (keywords.containsKey(identifier)) {
            this.makeToken(keywords.get(identifier));
            return;
        }

        this.makeToken(TokenKind.IDENTIFIER, identifier);
    }
}
