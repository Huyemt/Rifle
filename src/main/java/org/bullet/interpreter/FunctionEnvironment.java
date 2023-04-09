package org.bullet.interpreter;

import org.bullet.compiler.ast.nodes.BlockNode;

import java.util.HashMap;

/**
 * 函数执行的过程对象<br><br>
 *
 * @author Huyemt
 */

public class FunctionEnvironment {
    public final HashMap<String, Object> params;
    public BlockNode body;
    public Scope from;

    public FunctionEnvironment() {
        params = new HashMap<>();
        body = null;
        from = null;
    }
}
