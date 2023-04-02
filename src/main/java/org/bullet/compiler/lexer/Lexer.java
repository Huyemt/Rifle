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

    public final Position position;
    public Token currentToken;
    public static final HashMap<String, TokenKind> tokens = new HashMap<>();

    static {
        tokens.put("\0", TokenKind.EOF);
        tokens.put("+", TokenKind.PLUS);
        tokens.put("-", TokenKind.MINUS);
        tokens.put("*", TokenKind.STAR);
        tokens.put("/", TokenKind.SLASH);
        tokens.put("(", TokenKind.SLPAREN);
        tokens.put(")", TokenKind.SRPAREN);
    }

    public Lexer(String source) throws ParsingException {
        position = new Position(source);

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
            position.next();
        }

        if (tokens.containsKey(position.currentChar.toString())) {
            this.makeToken(tokens.get(position.currentChar.toString()));
            position.next();
            return;
        }

        if (Character.isDigit(position.currentChar)) {
            this.makeNumber();
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

        this.makeToken(TokenKind.VT_NUMBER, position.source.substring(start, position.index));
    }
}
