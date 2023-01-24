package org.rifle.library.javascript4j;

import javax.script.*;

/**
 * @author Huyemt
 */

public class ScriptExecutor implements Invocable {
    protected ScriptEngine engine;

    public ScriptExecutor(ScriptEngine engine) {
        changeEngine(engine);
    }

    public ScriptExecutor changeEngine(ScriptEngine engine) {
        if (engine != null) {
            this.engine = engine;
        } else
            throw new NullPointerException("The value of ScriptEngine passed to ScriptExecutor is null.");
        return this;
    }

    @Override
    public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
        return ((Invocable) engine).invokeMethod(thiz, name, args);
    }

    @Override
    public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
        return ((Invocable) engine).invokeFunction(name, args);
    }


    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////


    @Override
    public <T> T getInterface(Class<T> clasz) {
        return ((Invocable) engine).getInterface(clasz);
    }

    @Override
    public <T> T getInterface(Object thiz, Class<T> clasz) {
        return ((Invocable) engine).getInterface(thiz, clasz);
    }


    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////


    public Object get(String key) {
        return engine.get(key);
    }


    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////


    public Bindings getBindings(int scope) {
        return engine.getBindings(scope);
    }


    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////


    public ScriptContext getContext() {
        return engine.getContext();
    }
}
