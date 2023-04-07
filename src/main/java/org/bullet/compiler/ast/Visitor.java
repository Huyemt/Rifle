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
    public abstract Object goAssign(AssignNode node) throws RuntimeException;
    public abstract Object goIf(IfNode node) throws RuntimeException;
    public abstract Object goBlock(BlockNode node) throws RuntimeException;
    public abstract Object goWhile(WhileNode node) throws RuntimeException;
    public abstract Object goFor(ForNode node) throws RuntimeException;
    public abstract Object goUntil(UntilNode node) throws RuntimeException;
    public abstract Object goFunction(FunctionNode node) throws RuntimeException;
}
