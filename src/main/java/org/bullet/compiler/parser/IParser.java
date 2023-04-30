package org.bullet.compiler.parser;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.exceptions.common.ParsingException;

/**
 * @author Huyemt
 */

public interface IParser {
    /**
     * 解析程序
     * <br><br>
     * Parsing program node
     *
     * @return ProgramNode
     * @throws ParsingException
     */
    ProgramNode Parse() throws ParsingException;

    /**
     * 解析函数声明
     * <br><br>
     * Parsing function declaration node
     *
     * @return Node
     * @throws ParsingException
     */
    Node Function() throws ParsingException;

    /**
     * 解析 return
     * <br><br>
     * Parsing return node
     *
     * @return ReturnNode
     * @throws ParsingException
     */
    ReturnNode Return() throws ParsingException;

    /**
     * 解析函数调用
     * <br><br>
     * Parsing function call node
     *
     * @return
     * @throws ParsingException
     */
    Node FunctionCall() throws ParsingException;

    /**
     * 解析语句
     * <br><br>
     * Parsing statement node
     *
     * @return Node
     * @throws ParsingException
     */
    Node Statement() throws ParsingException;

    /**
     * 解析判断语句
     * <br><br>
     * Parsing if statement node
     *
     * @return IfNode
     * @throws ParsingException
     */
    IfNode If() throws ParsingException;

    /**
     * 解析 for 语句
     * <br><br>
     * Parsing for statement node
     *
     * @return ForNode
     * @throws ParsingException
     */
    ForNode For() throws ParsingException;

    /**
     * 解析 until 语句
     * <br><br>
     * Parsing until statement node
     *
     * @return UntilNode
     * @throws ParsingException
     */
    UntilNode Until() throws ParsingException;

    /**
     * 解析 break 语句
     * <br><br>
     * Parsing break statement node
     *
     * @return BreakNode
     * @throws ParsingException
     */
    BreakNode Break() throws ParsingException;

    /**
     * 解析 continue 语句
     * <br><br>
     * Parsing continue statement node
     *
     * @return ContinueNode
     * @throws ParsingException
     */
    ContinueNode Continue() throws ParsingException;

    /**
     * 解析代码块
     * <br><br>
     * Parsing code block
     *
     * @return BlockNode
     * @throws ParsingException
     */
    BlockNode Block() throws ParsingException;

    /**
     * 解析赋值语句
     * <br><br>
     * Parsing assignment statement
     *
     * @return Node
     * @throws ParsingException
     */
    Node Assign() throws ParsingException;
    IndexNode Index() throws ParsingException;
    Node ArrayCall() throws ParsingException;
    DictionaryNode Dictionary() throws ParsingException;
    Node LogicTerm() throws ParsingException;
    Node LogicFactor() throws ParsingException;
    Node Equal() throws ParsingException;
    Node Relational() throws ParsingException;
    Node Expression() throws ParsingException;
    Node Term() throws ParsingException;
    Node Factor() throws ParsingException;
    Node Unary() throws ParsingException;
    Node Involution() throws ParsingException;
    Node Secondary() throws ParsingException;
    Node Primary() throws ParsingException;
}
