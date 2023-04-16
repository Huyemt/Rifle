package org.bullet.base.components;

import org.bullet.compiler.ast.nodes.BlockNode;

import java.util.HashMap;

/**
 * 函数执行的环境
 * <br><br>
 * Function execution environment
 * @author Huyemt
 */

public class FunctionEnvironment {
    public final HashMap<String, Object> params;
    public BlockNode body;
    public BtScope from;

    public FunctionEnvironment() {
        params = new HashMap<>();
        body = null;
        from = null;
    }
}
