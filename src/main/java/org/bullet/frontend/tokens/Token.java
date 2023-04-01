package org.bullet.frontend.tokens;

/**
 * 词法单元<br><br>
 *
 * Token
 *
 * @author Huyemt
 */

public class Token {
    /**
     * 词法单元的类别属性<br><br>
     *
     * The name attribute of the token
     *
     */
    protected final TokenKind kind;

    public Token(TokenKind kind) {
        this.kind = kind;
    }

    public final TokenKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        switch (getKind()) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case STAR:
                return "*";
            case SLASH:
                return "/";
            default:
                return "";
        }
    }
}