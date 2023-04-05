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
    public ProgramNode Parse() throws ParsingException {
        ProgramNode node = new ProgramNode();
        node.position = lexer.position.clone();

        while (lexer.currentToken.kind != TokenKind.EOF) {
            node.statements.add(this.Statement());
        }

        return node;
    }

    @Override
    public Node Function() throws ParsingException {
        if (lexer.currentToken.kind == TokenKind.FUNCTION) {
            FunctionNode node = new FunctionNode();
            node.position = lexer.position.clone();

            lexer.next();

            if (!(lexer.currentToken instanceof VToken)) {
                String value = lexer.currentToken.toString();

                if (Lexer.tokens.containsKey(value)) {
                    throw new ParsingException(lexer.position, lexer.currentToken.kind == TokenKind.SLPAREN ? "Missing function name" : String.format("'%s' is not an identifier", value));
                }

                if (Lexer.keywords.containsKey(value)) {
                    throw new ParsingException(lexer.position, String.format("\"%s\" is a keyword and cannot be used as the name of a function", value));
                }

                throw new ParsingException(lexer.position, "Unrecognized function name");
            }

            // 如果当前的词法单元不是标识符
            if (lexer.currentToken.kind != TokenKind.IDENTIFIER) {
                throw new ParsingException(lexer.position, "Function name must be an identifier");
            }

            node.name = ((VToken) lexer.currentToken).value;
            lexer.expectToken(TokenKind.IDENTIFIER);
            lexer.expectToken(TokenKind.SLPAREN);

            if (lexer.currentToken.kind != TokenKind.SRPAREN) {
                while (lexer.currentToken.kind != TokenKind.SRPAREN) {
                    if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
                        String paramName = ((VToken) lexer.currentToken).value;

                        if (node.params.contains(paramName)) {
                            throw new ParsingException(lexer.position, String.format("The parameter \"%s\" have been defined in this function", paramName));
                        }

                        node.params.add(paramName);
                        lexer.expectToken(TokenKind.IDENTIFIER);
                        continue;
                    }

                    if (lexer.currentToken.kind == TokenKind.COMMA) {
                        lexer.next();
                        continue;
                    }

                    throw new ParsingException(lexer.position, "Incorrect function parameter definition behavior");
                }
            }

            lexer.expectToken(TokenKind.SRPAREN);

            if (lexer.currentToken.kind != TokenKind.BLPAREN) {
                throw new ParsingException(lexer.position, "The statement of the function must be written between '{' and '}'");
            }

            node.blockNode = (BlockNode) this.Statement();

            return node;
        }

        return this.Statement();
    }

    @Override
    public Node Statement() throws ParsingException {
        /*
        解析 if 选择体
         */
        if (lexer.currentToken.kind == TokenKind.IF) {
            IfNode node = new IfNode();
            node.position = lexer.position.clone();

            lexer.next();

            lexer.expectToken(TokenKind.SLPAREN);

            node.condition = this.Expression();

            lexer.expectToken(TokenKind.SRPAREN);

            node.body = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                lexer.next();

                node.elseBody = this.Statement();
            }

            return node;
        }

        /*
        解析 while 循环体
         */
        if (lexer.currentToken.kind == TokenKind.WHILE) {
            WhileNode node = new WhileNode();
            node.position = lexer.position.clone();

            lexer.next();

            lexer.expectToken(TokenKind.SLPAREN);

            node.condition = this.Expression();

            lexer.expectToken(TokenKind.SRPAREN);

            node.body = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                lexer.next();

                node.elseBody = this.Statement();
            }

            return node;
        }

        /*
        解析 for 循环体
         */
        if (lexer.currentToken.kind == TokenKind.FOR) {
            ForNode node = new ForNode();
            node.position = lexer.position.clone();

            lexer.next();

            lexer.expectToken(TokenKind.SLPAREN);

            if (lexer.currentToken.kind != TokenKind.SEMICOLON) {
                node.init = this.Expression();
            }
            lexer.expectToken(TokenKind.SEMICOLON);

            if (lexer.currentToken.kind != TokenKind.SEMICOLON) {
                node.condition = this.Expression();
            }
            lexer.expectToken(TokenKind.SEMICOLON);

            if (lexer.currentToken.kind != TokenKind.SRPAREN) {
                node.increase = this.Expression();
            }
            lexer.expectToken(TokenKind.SRPAREN);

            node.body = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                lexer.next();

                node.elseBody = this.Statement();
            }

            return node;
        }

        /*
        解析函数
         */
        if (lexer.currentToken.kind == TokenKind.FUNCTION) {
            // Bullet规定函数声明必须使用function关键字
            return this.Function();
        }

        /*
        解析变量赋值
         */
        if (lexer.currentToken.kind == TokenKind.VAR) {
            // Bullet规定变量声明必须使用var关键字
            return this.Assign();
        }

        /*
        解析代码块
         */
        if (lexer.currentToken.kind == TokenKind.BLPAREN) {
            BlockNode node = new BlockNode();
            node.position = lexer.position.clone();

            lexer.next();

            /*
            解析代码块，直到当前词法单元是右花括号
             */
            while (lexer.currentToken.kind != TokenKind.BRPAREN) {
                node.statements.add(this.Statement());
            }

            lexer.expectToken(TokenKind.BRPAREN);
            return node;
        }

        /*
        解析语句
         */
        StatementNode node = new StatementNode();
        node.position = lexer.position.clone();

        if (lexer.currentToken.kind != TokenKind.SEMICOLON) {
            node.left = this.Expression();
        }

        lexer.expectToken(TokenKind.SEMICOLON);

        return node;
    }

    @Override
    public Node Assign() throws ParsingException {
        boolean createAction = false;

        if (lexer.currentToken.kind == TokenKind.VAR) {
            lexer.next();

            createAction = true;
        }

        Node left = this.Equal();

        if (lexer.currentToken.kind == TokenKind.ASSIGN) {
            AssignNode node = new AssignNode();
            node.createAction = createAction;
            node.position = lexer.position.clone();

            lexer.next();

            node.left = left;
            node.right = this.Assign();

            return node;
        }

        return left;
    }

    @Override
    public Node Equal() throws ParsingException {
        Node left = this.Relational();

        while (lexer.currentToken.kind == TokenKind.EQUAL || lexer.currentToken.kind == TokenKind.NOT_EQUAL) {
            BinaryNode node = new BinaryNode();
            node.position = lexer.position.clone();
            node.operator = lexer.currentToken.kind == TokenKind.EQUAL ? BinaryNode.Operator.EQUAL : BinaryNode.Operator.NOT_EQUAL;
            lexer.next();

            node.left = left;
            node.right = this.Relational();

            left = node;
        }

        return left;
    }

    @Override
    public Node Relational() throws ParsingException {
        Node left = this.Term();

        while (lexer.currentToken.kind == TokenKind.GREATER || lexer.currentToken.kind == TokenKind.GREATER_OR_EQUAL || lexer.currentToken.kind == TokenKind.LESSER || lexer.currentToken.kind == TokenKind.LESSER_OR_EQUAL) {
            BinaryNode node = new BinaryNode();
            node.position = lexer.position.clone();

            if (lexer.currentToken.kind == TokenKind.GREATER)
                node.operator = BinaryNode.Operator.GREATER;
            else if (lexer.currentToken.kind == TokenKind.GREATER_OR_EQUAL)
                node.operator = BinaryNode.Operator.GREATER_OR_EQUAL;
            else if (lexer.currentToken.kind == TokenKind.LESSER)
                node.operator = BinaryNode.Operator.LESSER;
            else
                node.operator = BinaryNode.Operator.LESSER_OR_EQUAL;

            lexer.next();

            node.left = left;
            node.right = this.Term();

            left = node;
        }

        return left;
    }

    @Override
    public Node Expression() throws ParsingException {
        return this.Assign();
    }

    @Override
    public Node Term() throws ParsingException {
        Node left = this.Factor();

        while (lexer.currentToken.kind == TokenKind.PLUS || lexer.currentToken.kind == TokenKind.MINUS) {
            BinaryNode node = new BinaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.PLUS ? BinaryNode.Operator.ADD : BinaryNode.Operator.SUB;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = left;
            node.right = this.Factor();
            left = node;
        }

        return left;
    }

    @Override
    public Node Factor() throws ParsingException {
        Node left = this.Unary();

        while (lexer.currentToken.kind == TokenKind.STAR || lexer.currentToken.kind == TokenKind.SLASH) {
            BinaryNode node = new BinaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.STAR ? BinaryNode.Operator.MUL : BinaryNode.Operator.DIV;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = left;
            node.right = this.Unary();
            left = node;
        }

        return left;
    }

    @Override
    public Node Unary() throws ParsingException {
        if (lexer.currentToken.kind == TokenKind.PLUS || lexer.currentToken.kind == TokenKind.MINUS) {
            UnaryNode node = new UnaryNode();
            node.operator = lexer.currentToken.kind == TokenKind.PLUS ? UnaryNode.Operator.PLUS : UnaryNode.Operator.MINUS;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = this.Unary();

            return node;
        }

        return this.Primary();
    }

    @Override
    public Node Primary() throws ParsingException {
        if (lexer.currentToken.kind == TokenKind.VT_NUMBER) {
            ConstantNode<BigDecimal> node = new ConstantNode<>();
            node.value = new BigDecimal(((VToken)lexer.currentToken).value);
            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.SLPAREN) {
            lexer.next();
            Node node = this.Expression();
            lexer.expectToken(TokenKind.SRPAREN);

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
            VariableNode node = new VariableNode();
            node.name = ((VToken) lexer.currentToken).value;

            if (Lexer.keywords.containsKey(node.name)) {
                throw new ParsingException(lexer.position, "\"%s\" is a keyword and cannot be used as the name of a variable");
            }

            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        throw new ParsingException(lexer.position, String.format("Unsupported token \"%s\"", lexer.currentToken));
    }
}
