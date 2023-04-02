package org.bullet.compiler.lexer;

/**
 * @author Huyemt
 */

public class Token {
    public final TokenKind kind;
    public final Position position;

    public Token(TokenKind kind, Position position) {
        this.kind = kind;
        this.position = position.clone();
    }

    public final TokenKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return this instanceof VToken ? this.toString() : kind.toString();
    }
}
