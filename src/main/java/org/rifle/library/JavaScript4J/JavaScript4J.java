package org.rifle.library.JavaScript4J;

import javax.script.*;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class JavaScript4J {
    private static final ScriptEngineManager manager = new ScriptEngineManager();
    private static final Compilable PUBLIC_JAVASCRIPT_COMPILE_INTERFACE = (Compilable) manager.getEngineByName("javascript");
    private static final Pattern VERIFY = Pattern.compile("^.+\\.js$");

    public static ScriptEngine createEngine() {
        return manager.getEngineByName("javascript");
    }

    /**
     * 加载JavaScript文件
     *
     * Load JavaScript file
     *
     * @param file
     * @return ScriptExecutor
     * @throws Exception
     */
    public static ScriptExecutor load(File file) throws Exception {
        if (!VERIFY.matcher(file.getName()).matches())
            throw new Exception("JavaScript4J only allows loading JavaScript files.");
        ScriptEngine engine = createEngine();
        engine.eval(new FileReader(file));
        return new ScriptExecutor(engine);
    }

    /**
     * 加载JavaScript代码
     *
     * Load JavaScript code
     *
     * @param script
     * @return ScriptExecutor
     * @throws Exception
     */
    public static ScriptExecutor load(String script) throws Exception {
        ScriptEngine engine = createEngine();
        engine.eval(script);
        return new ScriptExecutor(engine);
    }

    /**
     * 编译JavaScript文件
     *
     * Compile JavaScript files
     *
     * @param script
     * @return CompiledScript
     * @throws Exception
     */
    public static CompiledScript compile(String script) throws Exception {
        return PUBLIC_JAVASCRIPT_COMPILE_INTERFACE.compile(script);
    }

    /**
     * 编译JavaScript代码
     *
     * Compile JavaScript code
     *
     * @param file
     * @return CompiledScript
     * @throws Exception
     */
    public static CompiledScript compile(File file) throws Exception {
        if (!VERIFY.matcher(file.getName()).matches())
            throw new Exception("JavaScript4J only allows loading JavaScript files.");
        return PUBLIC_JAVASCRIPT_COMPILE_INTERFACE.compile(new FileReader(file));
    }

    /**
     * 执行JavaScript文件
     *
     * Execute JavaScript files
     *
     * @param script
     * @return Object
     * @throws Exception
     */
    public static Object eval(String script) throws Exception {
        return compile(script).eval();
    }

    /**
     * 执行JavaScript代码
     *
     * Execute JavaScript code
     *
     * @param file
     * @return Object
     * @throws Exception
     */
    public static Object eval(File file) throws Exception {
        if (!VERIFY.matcher(file.getName()).matches())
            throw new Exception("JavaScript4J only allows loading JavaScript files.");
        return compile(file).eval();
    }
}
