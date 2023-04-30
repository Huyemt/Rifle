package org.bullet.compiler.parser;

import org.bullet.base.components.BtBulitInFunction;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.lexer.Position;
import org.bullet.compiler.lexer.TokenKind;
import org.bullet.compiler.lexer.VToken;
import org.bullet.exceptions.common.ParsingException;
import org.bullet.interpreter.BulletRuntime;
import org.bullet.interpreter.Interpreter;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Huyemt
 */

public class Parser implements IParser {
    private final Lexer lexer;
    private int blockLevel;
    private int loopLevel;
    private int complexLevel;
    private int paramLevel;
    private boolean functionParsing;
    private final Interpreter interpreter;
    public HashMap<String, FunctionNode> functions;

    public Parser(Lexer lexer, Interpreter interpreter) {
        this.interpreter = interpreter;
        this.lexer = lexer;
        blockLevel = 1;
        loopLevel = 0;
        complexLevel = 0;
        paramLevel = 0;
        functionParsing = false;
        functions = new HashMap<>();
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
            if (functionParsing) {
                throw new ParsingException(lexer.position, "Embedded functions are not supported");
            }

            if (loopLevel > 0) {
                throw new ParsingException(lexer.position, "Functions cannot be declared in the body of a loop");
            }

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

            if (interpreter.runtime.functions.containsKey(node.name)) {
                throw new ParsingException(lexer.position, String.format("\"%s\" is a Bullet built-in function and cannot be declared again", node.name));
            }

            if (functions.containsKey(node.name)) {
                throw new ParsingException(node.position, String.format("The function \"%s\" has already been declared", node.name));
            }

            lexer.expectToken(TokenKind.IDENTIFIER);

            paramLevel++;

            if (lexer.currentToken.kind == TokenKind.SLPAREN) {
                lexer.expectToken(TokenKind.SLPAREN);

                if (lexer.currentToken.kind != TokenKind.SRPAREN) {
                    while (lexer.currentToken.kind != TokenKind.SRPAREN) {
                        if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
                            String paramName = ((VToken) lexer.currentToken).value;

                            if (node.params.containsKey(paramName)) {
                                throw new ParsingException(lexer.position, String.format("The parameter \"%s\" have been defined in the function\"%s\"", paramName, node.name));
                            }

                            lexer.beginPeek();
                            lexer.next();

                            if (lexer.currentToken.kind == TokenKind.ASSIGN) {
                                lexer.next();
                                lexer.endPeek();
                                lexer.next(2);
                                node.params.put(paramName, this.Primary());
                            } else {
                                lexer.endPeek();
                                node.params.put(paramName, null);
                                lexer.next();
                            }

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
            }

            if (lexer.currentToken.kind != TokenKind.BLPAREN) {
                throw new ParsingException(lexer.position, "The statements of the function must be written between '{' and '}'");
            }

            paramLevel--;

            functionParsing = true;
            node.blockNode = (BlockNode) this.Statement();
            functionParsing = false;

            functions.put(node.name, node);
        }

        return this.Statement();
    }

    @Override
    public ReturnNode Return() throws ParsingException {
        if (!functionParsing && loopLevel > 0) {
            throw new ParsingException(lexer.position, "The \"return\" keyword can only be used in functions and loop bodies of functions");
        }
        ReturnNode node = new ReturnNode();
        node.position = lexer.position.clone();
        lexer.next();

        if (lexer.currentToken.kind != TokenKind.SEMICOLON) {
            node.left = this.Expression();

            while (lexer.currentToken.kind == TokenKind.SEMICOLON) {
                lexer.next();
            }
        } else {
            node.left = null;
            lexer.next();
        }

        return node;
    }

    @Override
    public Node FunctionCall() throws ParsingException {
        FunctionCallNode node = new FunctionCallNode();

        node.position = lexer.position.clone();
        node.name = ((VToken) lexer.currentToken).value;

        lexer.checkToken(TokenKind.IDENTIFIER);
        String name = ((VToken) lexer.currentToken).value;

        lexer.expectToken(TokenKind.IDENTIFIER);

        lexer.expectToken(TokenKind.SLPAREN);

        // 内置函数
        if (BulletRuntime.builtInfunctions.containsKey(name)) {
            BtBulitInFunction function = BulletRuntime.builtInfunctions.get(name);
            BuiltInFunctionCallNode built = new BuiltInFunctionCallNode();
            built.position = node.position;
            built.name = name;
            built.args = matchParams(built, function.args);
            lexer.expectToken(TokenKind.SRPAREN);

            return built;
        }

        if (!functions.containsKey(name)) {
            throw new ParsingException(lexer.position, String.format("Function \"%s\" is undefined", name));
        }

        FunctionNode function = functions.get(name);

        node.args = matchParams(node, function.params);

        lexer.expectToken(TokenKind.SRPAREN);

        return node;
    }

    @Override
    public Node Statement() throws ParsingException {
        /*
        解析 if 选择体
         */
        if (lexer.currentToken.kind == TokenKind.IF) {
            return this.If();
        }

        /*
        解析 for 循环体
         */
        if (lexer.currentToken.kind == TokenKind.FOR) {
            return this.For();
        }

        /*
        解析 until 循环体
         */
        if (lexer.currentToken.kind == TokenKind.UNTIL) {
            return this.Until();
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
        解析 return 返回值
         */
        if (lexer.currentToken.kind == TokenKind.RETURN) {
            return this.Return();
        }

        /*
        解析 break 跳出循环
         */
        if (lexer.currentToken.kind == TokenKind.BREAK) {
            return this.Break();
        }

        /*
        解析 continue 跳过本次循环
         */
        if (lexer.currentToken.kind == TokenKind.CONTINUE) {
            return this.Continue();
        }

        /*
        解析 #
         */
        if (lexer.currentToken.kind == TokenKind.SHARP) {
            return this.Assign();
        }

        /*
        解析代码块
         */
        if (lexer.currentToken.kind == TokenKind.BLPAREN) {
            return this.Block();
        }

        /*
        解析语句
         */
        StatementNode node = new StatementNode();
        node.position = lexer.position.clone();

        if (lexer.currentToken.kind != TokenKind.SEMICOLON) {
            node.left = this.Expression();
        }

        while (lexer.currentToken.kind == TokenKind.SEMICOLON) {
            lexer.next();
        }

        return node;
    }

    @Override
    public IfNode If() throws ParsingException {
        IfNode node = new IfNode();
        node.position = lexer.position.clone();

        lexer.next();

        lexer.expectToken(TokenKind.SLPAREN);

        if (lexer.currentToken.kind == TokenKind.SRPAREN) {
            throw new ParsingException(lexer.position, "If statement is missing condition");
        }

        node.condition = this.Expression();

        lexer.expectToken(TokenKind.SRPAREN);

        node.body = this.Statement();

        if (lexer.currentToken.kind == TokenKind.ELSE) {
            lexer.next();

            node.elseBody = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                throw new ParsingException(lexer.position, "There can only be one else scope");
            }
        }

        return node;
    }

    @Override
    public ForNode For() throws ParsingException {
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

        loopLevel++;

        node.body = this.Statement();

        if (lexer.currentToken.kind == TokenKind.ELSE) {
            lexer.next();

            node.elseBody = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                throw new ParsingException(lexer.position, "There can only be one else scope");
            }
        }

        loopLevel--;

        return node;
    }

    @Override
    public UntilNode Until() throws ParsingException {
        UntilNode node = new UntilNode();
        node.position = lexer.position.clone();

        lexer.next();

        lexer.expectToken(TokenKind.SLPAREN);

        if (lexer.currentToken.kind == TokenKind.SRPAREN) {
            throw new ParsingException(lexer.position, "Until statement is missing purpose");
        }

        loopLevel++;

        node.purpose = this.Expression();

        lexer.expectToken(TokenKind.SRPAREN);

        node.body = this.Statement();

        if (lexer.currentToken.kind == TokenKind.ELSE) {
            lexer.next();

            node.elseBody = this.Statement();

            if (lexer.currentToken.kind == TokenKind.ELSE) {
                throw new ParsingException(lexer.position, "There can only be one else scope");
            }
        }

        loopLevel--;

        return node;
    }

    @Override
    public BreakNode Break() throws ParsingException {
        if (loopLevel == 0) {
            throw new ParsingException(lexer.position, "Syntax error");
        }

        BreakNode node = new BreakNode();
        node.position = lexer.position.clone();
        lexer.next();

        while (lexer.currentToken.kind == TokenKind.SEMICOLON) {
            lexer.next();
        }

        return node;
    }

    @Override
    public ContinueNode Continue() throws ParsingException {
        if (loopLevel == 0) {
            throw new ParsingException(lexer.position, "Syntax error");
        }

        ContinueNode node = new ContinueNode();
        node.position = lexer.position.clone();
        lexer.next();

        while (lexer.currentToken.kind == TokenKind.SEMICOLON) {
            lexer.next();
        }
        return node;
    }

    @Override
    public BlockNode Block() throws ParsingException {
        BlockNode node = new BlockNode();
        node.position = lexer.position.clone();

        lexer.next();

        node.level = blockLevel;
        blockLevel++;

        /*
        解析代码块，直到当前词法单元是右花括号
         */
        while (lexer.currentToken.kind != TokenKind.BRPAREN) {
            node.statements.add(this.Statement());
        }

        lexer.expectToken(TokenKind.BRPAREN);
        blockLevel--;

        return node;
    }

    @Override
    public Node Assign() throws ParsingException {
        boolean createAction = false;
        boolean canChange = true;
        boolean provide = false;

        if (lexer.currentToken.kind == TokenKind.VAR) {
            lexer.next();

            createAction = true;
        }

        if (lexer.currentToken.kind == TokenKind.SHARP) {
            lexer.next();

            createAction = true;
            canChange = false;
            provide = true;
        }

        Node left = this.LogicTerm();

        if (lexer.currentToken.kind == TokenKind.C_ASSIGN) {
            if (!(left instanceof VariableNode)) {
                throw new ParsingException(lexer.position, "Only variables can be assigned values");
            }

            if (provide) {
                throw new ParsingException(lexer.position, "Variable decorated with \"#\" cannot be assigned with \":=\"");
            }

            AssignNode node = new AssignNode();
            node.createAction = true;
            node.canChange = true;
            node.isProvide = false;
            node.position = lexer.position.clone();

            lexer.next();

            node.left = left;
            node.right = this.Assign();

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.ASSIGN) {
            if (!(left instanceof VariableNode)) {
                throw new ParsingException(lexer.position, "Only variables can be assigned values");
            }

            AssignNode node = new AssignNode();
            node.createAction = createAction;
            node.canChange = canChange;
            node.isProvide = provide;
            node.position = lexer.position.clone();

            lexer.next();

            node.left = left;
            node.right = provide ? this.Primary() : this.Assign();

            return node;
        }

        if (lexer.currentToken.kind == TokenKind.ASSIGN_ADD || lexer.currentToken.kind == TokenKind.ASSIGN_SUB || lexer.currentToken.kind == TokenKind.ASSIGN_MUL || lexer.currentToken.kind == TokenKind.ASSIGN_DIV || lexer.currentToken.kind == TokenKind.ASSIGN_POW) {
            if (!(left instanceof VariableNode)) {
                throw new ParsingException(lexer.position, "Only variables can be assigned values");
            }

            if (createAction) {
                throw new ParsingException(lexer.position, "It is not allowed to use compound assignment when declaring variables");
            }

            complexLevel++;

            AssignNode node = new AssignNode();
            node.complexLevel = complexLevel;
            node.createAction = false;
            node.canChange = true;
            node.isProvide = false;
            node.position = lexer.position.clone();
            node.left = left;

            BinaryNode binaryNode = new BinaryNode();
            binaryNode.position = node.position;

            if (lexer.currentToken.kind == TokenKind.ASSIGN_ADD)
                binaryNode.operator = BinaryNode.Operator.ADD;
            else if (lexer.currentToken.kind == TokenKind.ASSIGN_SUB)
                binaryNode.operator = BinaryNode.Operator.SUB;
            else if (lexer.currentToken.kind == TokenKind.ASSIGN_MUL)
                binaryNode.operator = BinaryNode.Operator.MUL;
            else if (lexer.currentToken.kind == TokenKind.ASSIGN_DIV)
                binaryNode.operator = BinaryNode.Operator.DIV;
            else if (lexer.currentToken.kind == TokenKind.ASSIGN_POW)
                binaryNode.operator = BinaryNode.Operator.POW;
            else
                throw new ParsingException(binaryNode.position, "Syntax error");

            binaryNode.left = left;

            lexer.next();

            binaryNode.right = this.Assign();

            node.right = binaryNode;

            complexLevel--;

            return node;
        }

        return left;
    }

    @Override
    public IndexNode Index() throws ParsingException {
        IndexNode indexNode = null;
        IndexNode start = null;

        while (lexer.currentToken.kind == TokenKind.MLPAREN) {
            if (start == null) {
                start = new IndexNode();
                start.position = lexer.position.clone();

                indexNode = start;
            } else {
                indexNode.next = new IndexNode();
                indexNode = indexNode.next;
                indexNode.position = lexer.position.clone();
            }

            lexer.next();

            if (lexer.currentToken.kind != TokenKind.MRPAREN) {
                while (lexer.currentToken.kind != TokenKind.MRPAREN) {
                    if (lexer.currentToken.kind == TokenKind.COLON) {
                        if (indexNode.complex) {
                            throw new ParsingException(lexer.position, "Syntax error");
                        }

                        indexNode.complex = true;
                        lexer.next();
                    }

                    if (lexer.currentToken.kind != TokenKind.MRPAREN) {
                        if (indexNode.complex) {
                            indexNode.end = this.Assign();
                        } else {
                            if (indexNode.start == null) {
                                indexNode.start = this.Assign();
                            } else {
                                throw new ParsingException(lexer.position, "Syntax error");
                            }
                        }
                    }
                }
            }

            lexer.expectToken(TokenKind.MRPAREN);
        }

        return start;
    }

    @Override
    public Node ArrayCall() throws ParsingException {
        VariableNode variable = new VariableNode();
        variable.position = lexer.position.clone();
        variable.name = ((VToken) lexer.currentToken).value;
        lexer.next(); // identifier

        variable.indexNode = this.Index();

        return variable;
    }

    @Override
    public DictionaryNode Dictionary() throws ParsingException {
        DictionaryNode node = new DictionaryNode();
        node.position = lexer.position.clone();

        lexer.next();

        if (lexer.currentToken.kind != TokenKind.BRPAREN) {
            Node key;
            Node value;

            while (lexer.currentToken.kind != TokenKind.BRPAREN) {
                // 字符串键
                key = paramLevel > 0 ? this.Primary() : this.Assign();
                lexer.expectToken(TokenKind.COLON);
                value = paramLevel > 0 ? this.Primary() : this.Assign();

                node.vector.put(key, value);

                if (lexer.currentToken.kind != TokenKind.BRPAREN) {
                    lexer.expectToken(TokenKind.COMMA);
                }
            }

            lexer.expectToken(TokenKind.BRPAREN);

            return node;
        }

        lexer.next();

        return node;
    }

    @Override
    public Node LogicTerm() throws ParsingException {
        Node left = this.LogicFactor();

        while (lexer.currentToken.kind == TokenKind.AND) {
            BinaryNode node = new BinaryNode();
            node.position = lexer.position.clone();
            node.operator = BinaryNode.Operator.AND;
            lexer.next();

            node.left = left;
            node.right = this.LogicFactor();

            left = node;
        }

        return left;
    }

    @Override
    public Node LogicFactor() throws ParsingException {
        Node left = this.Equal();

        while (lexer.currentToken.kind == TokenKind.OR) {
            BinaryNode node = new BinaryNode();
            node.position = lexer.position.clone();
            node.operator = BinaryNode.Operator.OR;
            lexer.next();

            node.left = left;
            node.right = this.Equal();

            left = node;
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

        while (lexer.currentToken.kind == TokenKind.GREATER || lexer.currentToken.kind == TokenKind.GREATER_OR_EQUAL || lexer.currentToken.kind == TokenKind.LESSER || lexer.currentToken.kind == TokenKind.LESSER_OR_EQUAL || lexer.currentToken.kind == TokenKind.INSTANCEOF) {
            BinaryNode node = new BinaryNode();
            node.position = lexer.position.clone();
            node.operator = this.matchBinOpt();

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
        if (lexer.currentToken.kind == TokenKind.PLUS || lexer.currentToken.kind == TokenKind.MINUS || lexer.currentToken.kind == TokenKind.NOT || lexer.currentToken.kind == TokenKind.EXCLAMATION) {
            UnaryNode node = new UnaryNode();

            if (lexer.currentToken.kind == TokenKind.PLUS)
                node.operator = UnaryNode.Operator.PLUS;
            else if (lexer.currentToken.kind == TokenKind.MINUS)
                node.operator = UnaryNode.Operator.MINUS;
            else
                node.operator = UnaryNode.Operator.NOT;

            node.position = lexer.position.clone();
            lexer.next();
            node.left = this.Unary();

            return node;
        }

        return this.Involution();
    }

    @Override
    public Node Involution() throws ParsingException {
        Node left = this.Secondary();

        while (lexer.currentToken.kind == TokenKind.POW) {
            BinaryNode node = new BinaryNode();
            node.operator = BinaryNode.Operator.POW;
            node.position = lexer.position.clone();
            lexer.next();
            node.left = left;
            node.right = this.Secondary();
            left = node;
        }

        return left;
    }

    @Override
    public Node Secondary() throws ParsingException {

        // (
        if (lexer.currentToken.kind == TokenKind.SLPAREN) {
            lexer.next();
            Node node = this.Expression();
            lexer.expectToken(TokenKind.SRPAREN);

            return node;
        }

        // 标识符
        if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
            lexer.beginPeek();
            lexer.next();

            // 函数调用
            // IDENTIFIER ( Expression... )
            if (lexer.currentToken.kind == TokenKind.SLPAREN) {
                lexer.endPeek();

                return this.FunctionCall();
            }

            // 数组访问与字典访问
            // IDENTIFIER [ Expression ]
            if (lexer.currentToken.kind == TokenKind.MLPAREN) {
                lexer.endPeek();
                return this.ArrayCall();
            }

            lexer.endPeek();

            VariableNode node = new VariableNode();
            node.name = ((VToken) lexer.currentToken).value;

            if (Lexer.keywords.containsKey(node.name)) {
                throw new ParsingException(lexer.position, "\"%s\" is a keyword and cannot be used as the name of a variable");
            }

            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        return this.Primary();
    }

    @Override
    public Node Primary() throws ParsingException {
        // 数字
        if (lexer.currentToken.kind == TokenKind.VT_NUMBER) {
            ConstantNode node = new ConstantNode();
            node.value = new BigDecimal(((VToken)lexer.currentToken).value);
            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        // 字符串
        if (lexer.currentToken.kind == TokenKind.VT_STRING) {
            ConstantNode node = new ConstantNode();
            node.value = ((VToken)lexer.currentToken).value;
            node.position = lexer.position.clone();
            lexer.next();

            node.indexNode = this.Index();

            if (node.indexNode != null && paramLevel > 0) {
                throw new ParsingException(node.position, "Syntax error");
            }

            return node;
        }

        // null
        if (lexer.currentToken.kind == TokenKind.NULL) {
            ConstantNode node = new ConstantNode();
            node.value = null;
            node.position.clone();
            lexer.next();

            return node;
        }

        // 数组 []
        if (lexer.currentToken.kind == TokenKind.MLPAREN) {
            ArrayNode array = new ArrayNode();
            array.position = lexer.position.clone();

            lexer.next();

            while (lexer.currentToken.kind != TokenKind.MRPAREN) {
                array.values.add(paramLevel > 0 ? this.Primary() : this.Assign());

                if (lexer.currentToken.kind != TokenKind.MRPAREN) {
                    lexer.expectToken(TokenKind.COMMA);
                }
            }

            lexer.expectToken(TokenKind.MRPAREN);

            return array;
        }

        // 字典
        if (lexer.currentToken.kind == TokenKind.BLPAREN) {
            return this.Dictionary();
        }

        // 布尔值 true 和 false
        if (lexer.currentToken.kind == TokenKind.TRUE || lexer.currentToken.kind == TokenKind.FALSE) {
            ConstantNode node = new ConstantNode();
            node.value = lexer.currentToken.kind == TokenKind.TRUE;
            node.position = lexer.position.clone();
            lexer.next();

            return node;
        }

        throw new ParsingException(lexer.position, "Syntax error");
    }

    /////////////////////////////////////
    /////////////////////////////////////
    /////////////////////////////////////

    private BinaryNode.Operator matchBinOpt() throws ParsingException {
        switch (lexer.currentToken.kind) {
            case ASSIGN_ADD:
                return BinaryNode.Operator.ADD;
            case ASSIGN_SUB:
                return BinaryNode.Operator.SUB;
            case ASSIGN_MUL:
                return BinaryNode.Operator.MUL;
            case ASSIGN_DIV:
                return BinaryNode.Operator.DIV;
            case ASSIGN_POW:
                return BinaryNode.Operator.POW;
            case INSTANCEOF:
                return BinaryNode.Operator.INSTANCEOF;
            default:
                throw new ParsingException(lexer.position, "Syntax error");
        }
    }

    private String matchParamName(ArrayList<String> data) {
        String r;

        for (int i = 0; i < data.size(); i++) {
            r = data.get(i);

            if (r != null) {
                data.set(i, null);
                return r;
            }

        }

        return null;
    }

    private <T> LinkedHashMap<String, Node> matchParams(Node node, LinkedHashMap<String, T> defined) throws ParsingException {
        LinkedHashMap<String, Node> result = new LinkedHashMap<>();

        if (defined.size() == 0) {
            if (lexer.currentToken.kind != TokenKind.SRPAREN) {
                throw new ParsingException(lexer.position, "Parameter number exceeds the maximum length of 0");
            }
        } else {
            if (lexer.currentToken.kind == TokenKind.SRPAREN) {
                for (Map.Entry<String, ?> entry : defined.entrySet()) {
                    if (entry.getValue() == null) {
                        throw new ParsingException(lexer.position, String.format("Missing parameter \"%s\"", entry.getKey()));
                    }
                }
            } else {
                ArrayList<String> params = new ArrayList<>(defined.keySet());
                ArrayList<String> macther = (ArrayList<String>) params.clone();

                for (String key : defined.keySet()) {
                    result.put(key, null);
                }

                int counter = 0;
                boolean flag = false;

                while (lexer.currentToken.kind != TokenKind.SRPAREN) {

                    if (counter == defined.size()) {
                        throw new ParsingException(lexer.position, String.format("Parameter number exceeds the maximum length of %d", defined.size()));
                    }

                    if (lexer.currentToken.kind == TokenKind.IDENTIFIER) {
                        flag = false;
                        lexer.beginPeek();
                        String param = ((VToken) lexer.currentToken).value;
                        Position posParam = lexer.position.clone();
                        lexer.next();

                        if (lexer.currentToken.kind == TokenKind.ASSIGN) {
                            lexer.endPeek();
                            lexer.next(2);

                            if (!defined.containsKey(param)) {
                                throw new ParsingException(posParam, String.format("Parameter \"%s\" does not exist", param));
                            } else {
                                if (result.get(param) != null) {
                                    throw new ParsingException(posParam, String.format("Parameter \"%s\" has already been defined", param));
                                }

                                macther.set(macther.indexOf(param), null);
                                result.put(param, this.Assign());
                            }

                            counter++;
                            continue;
                        }

                        lexer.endPeek();
                    } else if (lexer.currentToken.kind == TokenKind.COMMA) {
                        if (flag) {
                            throw new ParsingException(lexer.position, "Syntax error");
                        }

                        flag = true;
                        lexer.next();
                        continue;
                    }

                    flag = false;

                    String pName = matchParamName(macther);

                    if (pName == null) {
                        throw new ParsingException(lexer.position, String.format("Parameter number exceeds the maximum length of %d", defined.size()));
                    }

                    result.put(pName, this.Assign());

                    counter++;
                }

                for (String param : defined.keySet()) {
                    if (result.get(param) == null) {
                        T r = defined.get(param);

                        if ((r != null && !(r instanceof Node))) {
                            continue;
                        }

                        if (r == null) {
                            throw new ParsingException(node.position, String.format("Missing parameter \"%s\"", param));
                        }

                        result.put(param, (Node) r);
                    }
                }
            }
        }

        return result;
    }
}
