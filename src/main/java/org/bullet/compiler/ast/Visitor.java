package org.bullet.compiler.ast;

import org.bullet.compiler.ast.nodes.*;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public abstract class Visitor {
    public abstract Object goProgram(ProgramNode node) throws RuntimeException;
    public abstract Object goBinary(BinaryNode node) throws RuntimeException;
    public abstract Object goUnary(UnaryNode node) throws RuntimeException;
    public abstract Object goConstant(ConstantNode<?> node) throws RuntimeException;
    public abstract Object goStatement(StatementNode node) throws RuntimeException;
    public abstract Object goVariable(VariableNode node) throws RuntimeException;
    public abstract Object goAssign(AssginNode node) throws RuntimeException;
}
