package org.bullet.interpreter;

import org.bullet.base.bulitIn.function.*;
import org.bullet.base.bulitIn.function.data.BtDecJsonFunction;
import org.bullet.base.bulitIn.function.data.BtEncJsonFunction;
import org.bullet.base.bulitIn.function.security.*;
import org.bullet.base.bulitIn.function.net.*;
import org.bullet.base.bulitIn.function.type.*;
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
    public static final HashMap<String, BtBulitInFunction> builtInfunctions = new HashMap<>();
    public final HashMap<String, Object> provideAttributes;
    public final Stack<FunctionEnvironment> environments;
    public Object returnValue;
    public LoopStatus loopStatus;
    public int loopLevel;
    public Logger logger;

    public BulletRuntime() {
        functions = new HashMap<>();
        provideAttributes = new HashMap<>();
        returnValue = null;
        environments = new Stack<>();
        environment = null;
        loopStatus = LoopStatus.NONE;
        loopLevel = 0;
        logger = null;

        registerBuiltInFunctions();
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

    private void builtInFunction(BtBulitInFunction function) {
        builtInfunctions.put(function.getName(), function);
    }

    private void registerBuiltInFunctions() {
        builtInFunction(new BtPrintFunction(this));
        builtInFunction(new BtPrintlnFunction(this));
        builtInFunction(new BtLenFunction(this));
        builtInFunction(new BtStrFunction(this));
        builtInFunction(new BtNumFunction(this));
        builtInFunction(new BtBytesFunction(this));
        builtInFunction(new BtMd5Function(this));
        builtInFunction(new BtSha1Function(this));
        builtInFunction(new BtEncBase64Function(this));
        builtInFunction(new BtDecBase64Function(this));
        builtInFunction(new BtSha256Function(this));
        builtInFunction(new BtSha512Function(this));
        builtInFunction(new BtEncAESFunction(this));
        builtInFunction(new BtDecAESFunction(this));
        builtInFunction(new BtEncURLFunction(this));
        builtInFunction(new BtDecURLFunction(this));
        builtInFunction(new BtEncRSAFunction(this));
        builtInFunction(new BtDecRSAFunction(this));
        builtInFunction(new BtNGetFunction(this));
        builtInFunction(new BtNPostFunction(this));
        builtInFunction(new BtIsNumFunction(this));
        builtInFunction(new BtIsStrFunction(this));
        builtInFunction(new BtIsDictionaryFunction(this));
        builtInFunction(new BtIsListFunction(this));
        builtInFunction(new BtIsByteFunction(this));
        builtInFunction(new BtIsNullFunction(this));
        builtInFunction(new BtIsBooleanFunction(this));
        builtInFunction(new BtIsByteStringFunction(this));;
        builtInFunction(new BtTypeofFunction(this));
        builtInFunction(new BtEncJsonFunction(this));
        builtInFunction(new BtDecJsonFunction(this));
    }
}
