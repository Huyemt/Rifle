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
    TRUE("true"),
    FALSE("false"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    FOR("for"),
    UNTIL("until"),
    VAR("var"),
    AND("and"),
    OR("or"),
    NOT("not"),
    FUNCTION("function"),
    RETURN("return"),
    BREAK("break"),
    CONTINUE("continue"),

    /*
    运算符
    Operator
     */
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),
    POW("**"),
    EXCLAMATION("!"),
    L_AND("&"),
    L_OR("|"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER(">"),
    GREATER_OR_EQUAL(">="),
    LESSER("<"),
    LESSER_OR_EQUAL("<="),
    ASSIGN_ADD("+="),
    ASSIGN_SUB("-="),
    ASSIGN_MUL("*="),
    ASSIGN_DIV("/="),
    ASSIGN_POW("**="),


    /*
    界限符
    Delimiter
     */
    SLPAREN("("),
    SRPAREN(")"),
    BLPAREN("{"),
    BRPAREN("}"),
    ASSIGN("="),
    C_ASSIGN(":="),
    COMMA(","),
    POINT("."),
    SEMICOLON(";"),
    AT("@"),
    COLON(":"),

    EOF("EOF"),

    /*
    Const
    常量类型
     */
    VT_NUMBER("NUMBER"),
    VT_STRING("STRING"),
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
