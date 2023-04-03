package org.bullet.compiler.parser;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.ProgramNode;
import org.bullet.exceptions.ParsingException;

/**
 * @author Huyemt
 */

public interface IParser {
    ProgramNode parse() throws ParsingException;
    Node statement() throws ParsingException;
    Node assign() throws ParsingException;
    Node expression() throws ParsingException;
    Node term() throws ParsingException;
    Node factor() throws ParsingException;
    Node unary() throws ParsingException;
    Node prirmary() throws ParsingException;
}
