package org.bullet.compiler.ast;

import org.bullet.compiler.lexer.Position;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public abstract class Node {
    public Position position;

    public abstract Object accept(Visitor visitor) throws RuntimeException;
}
