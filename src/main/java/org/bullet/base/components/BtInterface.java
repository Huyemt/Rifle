package org.bullet.base.components;

import org.bullet.compiler.ast.nodes.BlockNode;
import org.bullet.compiler.ast.nodes.ProvideNode;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Interpreter;

/**
 * @author Huyemt
 */

public class BtInterface {
    private final ProvideNode node;
    private final Interpreter interpreter;

    public BtInterface(Interpreter interpreter, ProvideNode node) {
        this.interpreter = interpreter;
        this.node = node;
    }

    public final void invoke() throws RuntimeException {
        node.node.accept(interpreter);
    }

    public final String getName() {
        return node.name;
    }
}
