package org.bullet.base;


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
    public void call(Object ... args) throws BulletException {
        this.callFV(args);
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
    public Object callFV(Object ... args) throws BulletException {
        if (args.length > node.params.size()) {
            throw new BulletException(String.format("Too many parameters -> %d", args.length - node.params.size()));
        } else if (args.length < node.params.size()) {
            throw new BulletException(String.format("Missing parameters -> %d", node.params.size() - args.length));
        }

        FunctionEnvironment environment = new FunctionEnvironment();
        environment.body = node.blockNode;
        environment.from = interpreter.scope;

        for (int i = 0; i < node.params.size(); i++) {
            if (args[i] instanceof Integer || args[i] instanceof Float || args[i] instanceof Double) {
                args[i] = new BigDecimal(args[i].toString());
            }

            environment.params.put(node.params.get(i), args[i]);
        }

        if (interpreter.environment != null) {
            interpreter.environments.push(interpreter.environment);
        }

        interpreter.environment = environment;
        Object r = interpreter.environment.body.accept(interpreter);
        interpreter.returnValue = null;
        interpreter.environment = (interpreter.environments.size() > 0) ? interpreter.environments.pop() : null;

        return r;
    }

    public final String getName() {
        return node.name;
    }
}
