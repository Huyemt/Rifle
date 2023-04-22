package org.bullet.base.components;

import org.bullet.compiler.ast.nodes.FunctionNode;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.Interpreter;

import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class BtCustomFunction extends BtFunction {
    private final FunctionNode node;
    private final Interpreter interpreter;
    //private static final Pattern NUMBER_PARTTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");


    public BtCustomFunction(Interpreter interpreter, FunctionNode node) {
        super(node.name, interpreter.runtime);
        this.interpreter = interpreter;
        this.node = node;
    }

    @Override
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

            // 将 Java 的数组转换为 BtArray 类型
            if (args[i].getClass().isArray()) {
                args[i] = translateArray((Object[]) args[i]);
            }

            environment.params.put(node.params.get(i), args[i]);
        }

        if (runtime.environment != null) {
            runtime.environments.push(runtime.environment);
        }

        runtime.environment = environment;
        Object r = runtime.environment.body.accept(interpreter);
        runtime.returnValue = null;
        runtime.environment = (runtime.environments.size() > 0) ? runtime.environments.pop() : null;

        return r;
    }

    @Override
    public final String toString() {
        return "Custom".concat(super.toString());
    }
}
