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
    关键字
    Keyword
     */
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    FOR("for"),
    VAR("var"),
    AND("and"),
    OR("or"),
    FUNCTION("function"),

    /*
    运算符
    Operator
     */
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),
    EXCLAMATION("!"),
    L_AND("&"),
    L_OR("|"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER(">"),
    GREATER_OR_EQUAL(">="),
    LESSER("<"),
    LESSER_OR_EQUAL("<="),

    /*
    界限符
    Delimiter
     */
    SLPAREN("("),
    SRPAREN(")"),
    BLPAREN("{"),
    BRPAREN("}"),
    ASSIGN("="),
    COMMA(","),
    POINT("."),
    SEMICOLON(";"),

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
