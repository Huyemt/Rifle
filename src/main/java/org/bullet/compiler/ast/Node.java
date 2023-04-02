package org.bullet.compiler.ast;

import org.bullet.compiler.lexer.Position;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Result;

/**
 * @author Huyemt
 */

public abstract class Node {
    public Position position;

    public abstract Result<?> accept(Visitor visitor) throws RuntimeException;
}
