package org.bullet.base.components;


import org.bullet.compiler.ast.nodes.FunctionNode;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.Interpreter;

import java.math.BigDecimal;

/**
 * Bullet的函数
 * <br><br>
 * Function of Bullet
 *
 * @author Huyemt
 */

public class BtFunction {
    private final FunctionNode node;
    private final Interpreter interpreter;
    //private static final Pattern NUMBER_PARTTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");


    public BtFunction(Interpreter interpreter, FunctionNode node) {
        this.interpreter = interpreter;
        this.node = node;
    }

    /**
     * 函数调用
     * <br><br>
     * Call the function
     *
     * @param args
     * @throws BulletException
     */
    public void invoke(Object ... args) throws BulletException {
        this.invokeFV(args);
    }

    /**
     * 函数调用并获取返回值
     * <br><br>
     * Call the function and get the return value
     *
     * @param args
     * @return Object
     * @throws BulletException
     */
    public Object invokeFV(Object ... args) throws BulletException {
        if (args.length > node.params.size()) {
            throw new BulletException(String.format("Too many parameters -> %d", args.length - node.params.size()));
        } else if (args.length < node.params.size()) {
            throw new BulletException(String.format("Missing parameters -> %d", node.params.size() - args.length));
        }

        FunctionEnvironment environment = new FunctionEnvironment();
        environment.body = node.blockNode;
        environment.from = interpreter.runtime.scope;

        for (int i = 0; i < node.params.size(); i++) {
            if (args[i] instanceof Integer || args[i] instanceof Float || args[i] instanceof Double) {
                args[i] = new BigDecimal(args[i].toString());
            }

            environment.params.put(node.params.get(i), args[i]);
        }

        if (interpreter.runtime.environment != null) {
            interpreter.runtime.environments.push(interpreter.runtime.environment);
        }

        interpreter.runtime.environment = environment;
        Object r = interpreter.runtime.environment.body.accept(interpreter);
        interpreter.runtime.returnValue = null;
        interpreter.runtime.environment = (interpreter.runtime.environments.size() > 0) ? interpreter.runtime.environments.pop() : null;

        return r;
    }

    public final String getName() {
        return node.name;
    }
}
