package org.bullet.interpreter;

import org.bullet.base.bulitIn.function.BtLenFunction;
import org.bullet.base.bulitIn.function.BtPrintFunction;
import org.bullet.base.bulitIn.function.BtPrintlnFunction;
import org.bullet.base.bulitIn.function.BtStrFunction;
import org.bullet.base.components.*;
import org.bullet.compiler.ast.nodes.BlockNode;
import org.bullet.exceptions.common.UnderfineException;
import org.rifle.console.logger.Logger;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author Huyemt
 */

public class BulletRuntime {
    public BtScope scope;
    public FunctionEnvironment environment;
    public final HashMap<String, BtFunction> functions;
    public final HashMap<String, Object> provideAttributes;
    public final HashMap<String, BtInterface> provideInterfaces;
    public final Stack<FunctionEnvironment> environments;
    public Object returnValue;
    public LoopStatus loopStatus;
    public int loopLevel;
    public Logger logger;

    public BulletRuntime() {
        functions = new HashMap<>();
        provideAttributes = new HashMap<>();
        provideInterfaces = new HashMap<>();
        returnValue = null;
        environments = new Stack<>();
        environment = null;
        loopStatus = LoopStatus.NONE;
        loopLevel = 0;
        logger = null;

        functions.put("print", new BtPrintFunction(this));
        functions.put("println", new BtPrintlnFunction(this));
        functions.put("len", new BtLenFunction(this));
        functions.put("str", new BtStrFunction(this));
    }

    public BtScope createScope(BlockNode node) {
        BtScope btScope = new BtScope();
        btScope.position = node.position;
        btScope.node = node;
        btScope.from = this.scope;

        return btScope;
    }

    public boolean existsVariable(String name) {
        return scope.existsVariable(name);
    }

    public BtVariable findVariable(String name) throws UnderfineException {
        return scope.findVariable(name);
    }

    public boolean existsFunction(String name) {
        return functions.containsKey(name);
    }

    public BtFunction findFunction(String name) throws UnderfineException {
        if (!existsFunction(name)) {
            throw new UnderfineException(UnderfineException.UnderfineType.FUNCTION, name);
        }

        return functions.get(name);
    }

    public boolean existsAttribute(String name) {
        return provideAttributes.containsKey(name);
    }

    public Object findAttribute(String name) throws UnderfineException {
        if (!existsAttribute(name)) {
            throw new UnderfineException(UnderfineException.UnderfineType.PROVIDE_ATTRIBUTE, name);
        }

        return provideAttributes.get(name);
    }

    public boolean existsInterface(String name) {
        return provideInterfaces.containsKey(name);
    }

    public BtInterface findInterface(String name) throws UnderfineException {
        if (!existsInterface(name)) {
            throw new UnderfineException(UnderfineException.UnderfineType.PROVIDE_INTERFACE, name);
        }

        return provideInterfaces.get(name);
    }
}
