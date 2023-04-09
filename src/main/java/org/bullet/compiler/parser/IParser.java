package org.bullet.compiler.parser;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.ProgramNode;
import org.bullet.exceptions.ParsingException;

/**
 * @author Huyemt
 */

public interface IParser {
    ProgramNode Parse() throws ParsingException;
    Node Function() throws ParsingException;
    Node FunctionCall() throws ParsingException;
    Node Statement() throws ParsingException;
    Node Assign() throws ParsingException;
    Node LogicTerm() throws ParsingException;
    Node LogicFactor() throws ParsingException;
    Node Equal() throws ParsingException;
    Node Relational() throws ParsingException;
    Node Expression() throws ParsingException;
    Node Term() throws ParsingException;
    Node Factor() throws ParsingException;
    Node Unary() throws ParsingException;
    Node Involution() throws ParsingException;
    Node Primary() throws ParsingException;
}
