package org.bullet.frontend;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.node.BinaryNode;
import org.bullet.frontend.ast.node.ConstantNode;
import org.bullet.frontend.ast.node.ProgramNode;
import org.bullet.frontend.ast.node.UnaryNode;
import org.bullet.frontend.tokens.TokenKind;
import org.bullet.frontend.tokens.VToken;

import java.math.BigDecimal;

/**
 * 语法解析器<br><br>
 *
 * Parser
 *
 * @author Huyemt
 */

public class Parser extends IParser {
    /*
    首先，一个合格的解释器，需要有如下：
        1. 词法分析
        2. 语法分析
        3. 语义分析
        4. 生成目标代码
        5. 执行代码

    1~3 为前端工作
    4~5 为后端工作


    对于本词法解析器，您需要知道几个概念:
        1. 嵌入词法分析 (Lexer)
        2. 语法分析与语义分析是同时进行的
     */

    public Parser(Lexer lexer) {
        super(lexer);
    }

    @Override
    public ProgramNode parse() {
        ProgramNode node = new ProgramNode();
        node.left = this.expression();
        return node;
    }

    @Override
    protected INode expression() {
        return this.term();
    }

    @Override
    protected INode term() {
        INode left = this.factor();

        while (lexer.current.getKind() == TokenKind.PLUS || lexer.current.getKind() == TokenKind.MINUS) {
            BinaryNode.Operator operator = lexer.current.getKind() == TokenKind.PLUS ? BinaryNode.Operator.ADD : BinaryNode.Operator.SUB;
            lexer.next();
            BinaryNode node = new BinaryNode();
            node.operator = operator;
            node.left = left;
            node.right = this.factor();
            left = node;
        }

        return left;
    }

    @Override
    protected INode factor() {
        INode left = this.unary();

        while (lexer.current.getKind() == TokenKind.STAR || lexer.current.getKind() == TokenKind.SLASH) {
            BinaryNode.Operator operator = lexer.current.getKind() == TokenKind.STAR ? BinaryNode.Operator.MUL : BinaryNode.Operator.DIV;
            lexer.next();
            BinaryNode node = new BinaryNode();
            node.operator = operator;
            node.left = left;
            node.right = this.unary();
            left = node;
        }

        return left;
    }

    @Override
    protected INode primary() {
        if (lexer.current.getKind() == TokenKind.VT_NUMBER) {
            ConstantNode<BigDecimal> node = new ConstantNode<>();
            node.value = new BigDecimal(((VToken) lexer.current).getValue());
            lexer.next();
            return node;
        }

        return null;
    }

    @Override
    protected INode unary() {
        if (lexer.current.getKind() == TokenKind.PLUS || lexer.current.getKind() == TokenKind.MINUS) {
            UnaryNode node = new UnaryNode();
            node.operator = lexer.current.getKind() == TokenKind.PLUS ? UnaryNode.Operator.PLUS : UnaryNode.Operator.MINUS;
            lexer.next();
            node.left = this.unary();

            return node;
        }

        return this.primary();
    }
}
