package org.bullet.compiler.parser;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.lexer.TokenKind;
import org.bullet.compiler.lexer.VToken;
import org.bullet.exceptions.ParsingException;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class Parser implements IParser {

    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public ProgramNode parse() throws ParsingException {
        ProgramNode node = new ProgramNode();

        while (lexer.currentToken.kind != TokenKind.EOF) {
            node.statements.add(this.statement());
        }

        return node;
    }

    @Override
    public Node statement() throws ParsingException {
        StatementNode node = new StatementNode();

        node.left = this.expression();
        node.position = lexer.position.clone();

        lexer.expectToken(TokenKind.SEMICOLON);

        return node;
    }

    @Override
    public Node assign() throws ParsingException {
        Node left = this.term();

        if (lexer.currentToken.kind == TokenKind.ASSIGN) {
            lexer.next();

            AssginNode node = new AssginNode();
            node.position = lexer.position.clone();
            node.left = left;
            node.right = this.assign();

            return node;
        }

        return left;
    }

    @Override
    public Node expression() throws ParsingException {
        return this.assign();
    }

    @Override
    public Node term() throws ParsingException {
        Node left = this.factor();

        while (lexer.currentToken.kind == TokenKind.PLUS || lexer.currentToken.kind == TokenKind.MINUS) {
            BinaryNode node = new BinaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.PLUS ? BinaryNode.Operator.ADD : BinaryNode.Operator.SUB;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = left;
            node.right = this.factor();
            left = node;
        }

        return left;
    }

    @Override
    public Node factor() throws ParsingException {
        Node left = this.unary();

        while (lexer.currentToken.kind == TokenKind.STAR || lexer.currentToken.kind == TokenKind.SLASH) {
            BinaryNode node = new BinaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.STAR ? BinaryNode.Operator.MUL : BinaryNode.Operator.DIV;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = left;
            node.right = this.unary();
            left = node;
        }

        return left;
    }

    @Override
    public Node unary() throws ParsingException {
        if (lexer.currentToken.kind == TokenKind.PLUS || lexer.currentToken.kind == TokenKind.MINUS) {
            UnaryNode node = new UnaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.PLUS ? UnaryNode.Operator.PLUS : UnaryNode.Operator.MINUS;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = this.unary();

            return node;
        }

        return this.prirmary();
    }

    @Override
    public Node prirmary() throws ParsingException {
        if (lexer.currentToken.kind == TokenKind.VT_NUMBER) {
            ConstantNode<BigDecimal> node = new ConstantNode<>();
            node.value = new BigDecimal(((VToken)lexer.currentToken).value);
            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.SLPAREN) {
            lexer.next();
            Node node = this.expression();
            lexer.expectToken(TokenKind.SRPAREN);

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
            VariableNode node = new VariableNode();
            node.name = ((VToken) lexer.currentToken).value;
            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        throw new ParsingException(lexer.position, "Unsupported token");
    }
}
