package org.bullet.compiler.lexer;

/**
 * @author Huyemt
 */

public enum TokenKind {
    /*
    通用符
    Common
     */
    IDENTIFIER("IDENTIFIER"),

    /*
    运算符
    Operator
     */
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),

    /*
    界限符
    Delimiter
     */
    SLPAREN("("),
    SRPAREN(")"),
    EOF("EOF"),

    /*
    Const
    常量类型
     */
    VT_NUMBER("NUMBER")
    ;

    private final String value;

    TokenKind(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }

    @Override
    public final String toString() {
        return getValue();
    }
}
