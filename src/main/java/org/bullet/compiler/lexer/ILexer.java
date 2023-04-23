package org.bullet.compiler.lexer;

import org.bullet.exceptions.common.ParsingException;

/**
 * @author Huyemt
 */

public interface ILexer {
    /**
     * 获取下一个Token<br><br>
     *
     * Get the next Token
     *
     * @throws ParsingException
     */
    void next() throws ParsingException;

    /**
     * 创造一个无值的Token<br><br>
     *
     * Create a Token with no value
     *
     * @param kind
     */
    void makeToken(TokenKind kind);

    /**
     * 创造一个带值的Token<br><br>
     *
     * Create a Token with a value
     *
     * @param kind
     * @param value
     */
    void makeToken(TokenKind kind, String value);

    /**
     * 规定下一个Token的类型<br><br>
     *
     * Specifies the type of the next Token
     *
     * @param kind
     * @throws ParsingException
     */
    void expectToken(TokenKind kind) throws ParsingException;

    /**
     * 检查当前Token类型
     * <br><br>
     * Check the type of the current token
     *
     * @param kind
     * @throws ParsingException
     */
    void checkToken(TokenKind kind) throws ParsingException;

    /**
     * 读取相对于当前位置的第 distance 个字符<br><br>
     *
     * Read the distance character relative to the current position
     *
     * @param distance
     * @return char
     */
    char peekChar(int distance);

    void beginPeek();
    void endPeek();
}
