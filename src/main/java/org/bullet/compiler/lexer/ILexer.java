package org.bullet.compiler.lexer;

import org.bullet.exceptions.ParsingException;

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
}
