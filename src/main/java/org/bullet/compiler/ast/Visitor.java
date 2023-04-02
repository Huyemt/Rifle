package org.bullet.compiler.ast;

import org.bullet.compiler.ast.nodes.BinaryNode;
import org.bullet.compiler.ast.nodes.ConstantNode;
import org.bullet.compiler.ast.nodes.ProgramNode;
import org.bullet.compiler.ast.nodes.UnaryNode;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Result;

/**
 * @author Huyemt
 */

public abstract class Visitor {
    public abstract Result<?> goProgram(ProgramNode node) throws RuntimeException;
    public abstract Result<?> goBinary(BinaryNode node) throws RuntimeException;
    public abstract Result<?> goUnary(UnaryNode node) throws RuntimeException;
    public abstract Result<?> goConstant(ConstantNode<?> node) throws RuntimeException;
}
