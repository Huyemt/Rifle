package org.bullet.compiler.lexer;

/**
 * @author Huyemt
 */

public class VToken extends Token {

    public final String value;

    public VToken(TokenKind kind, Position position, String value) {
        super(kind, position);

        this.value = value;
    }

    public final String getValue() {
        return value;
    }

    @Override
    public final String toString() {
        return String.format("[%s: %s]", getKind().toString(), getValue());
    }
}
