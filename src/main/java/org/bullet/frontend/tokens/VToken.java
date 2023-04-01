package org.bullet.frontend.tokens;

/**
 * 带内容值的词法单元<br><br>
 *
 * The content value of a token
 *
 * @author Huyemt
 */

public class VToken extends Token {
    public final String value;

    public VToken(TokenKind kind, String value) {
        super(kind);
        this.value = value;
    }

    public final String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
