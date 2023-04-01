package org.bullet.frontend.ast;

import org.bullet.frontend.ast.node.BinaryNode;
import org.bullet.frontend.ast.node.ConstantNode;
import org.bullet.frontend.ast.node.ProgramNode;
import org.bullet.frontend.ast.node.UnaryNode;
import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public abstract class IVisitor {
    public abstract Result<?> goProgram(ProgramNode node);
    public abstract Result<?> goBinary(BinaryNode node);
    public abstract Result<?> goUnary(UnaryNode node);
    public abstract <T> Result<?> goConstant(ConstantNode<T> node);
}
