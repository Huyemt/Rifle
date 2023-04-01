package org.bullet.frontend;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.node.ProgramNode;

/**
 * 抽象语法解析器<br><br>
 *
 * Abstract parser
 *
 * @author Huyemt
 */

public abstract class IParser {
    protected final Lexer lexer;

    public IParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public abstract ProgramNode parse();

    /**
     * 解析一个表达式节点<br><br>
     *
     * Parse an expression
     *
     * @return INode
     */
    protected abstract INode expression();

    /**
     * 解析 相加 或 相减 的两个项的节点<br><br>
     *
     * Parse two terms that add or subtract
     *
     * @return INode
     */
    protected abstract INode term();

    /**
     * 解析 相乘 或 相除 的两个项的节点<br><br>
     *
     * Parse two terms that multiply or divide
     *
     * @return INode
     */
    protected abstract INode factor();

    /**
     * 解析一个主要节点<br><br>
     *
     * Parse a primary node
     *
     * @return INode
     */
    protected abstract INode primary();

    /**
     * 解析一个一元节点<br><br>
     *
     * Parse an unary node
     *
     * @return INode
     */
    protected abstract INode unary();
}
