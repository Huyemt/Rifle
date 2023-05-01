package org.bullet.base.components;

import org.bullet.base.types.BtType;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.nodes.FunctionNode;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<Object> as = new ArrayList<>(Arrays.asList(args));

        if (as.size() < node.params.size()) {

            String[] params = node.params.keySet().toArray(String[]::new);

            for (int start = as.size(); start < params.length; start++) {
                Node node1 = node.params.get(params[start]);

                if (node1 == null) {
                    throw new BulletException(String.format("The parameter \"%s\"has already been defined", params[start]));
                }

                as.add(node1.accept(interpreter));
            }
        }

        args = as.toArray();

        FunctionEnvironment environment = new FunctionEnvironment();
        environment.body = node.blockNode;
        environment.from = interpreter.runtime.scope;

        int i = 0;
        for (String name : node.params.keySet()) {
            environment.params.put(name, BtType.jTypeToBType(args[i]));
            i++;
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
