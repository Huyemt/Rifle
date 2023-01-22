# 前言
自从`JDK1.6`版本及其之后，`Java`支持调用其它脚本语言。
<br>
本支持库在此基础之上，提供了更加方便调用`JavaScript`的封装方法。
<br><br>
[阅读源码](../../../src/main/java/org/rifle/library/JavaScript4J)
***
## 直接获得结果
```java
import org.rifle.library.JavaScript4J.JavaScript4J;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * 方法一，传递JavaScript文件
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        System.out.println(JavaScript4J.eval(file));

        /**
         * 方法二，传入代码
         */
        String script = "function test(a,b) { return a+b; } test(1,2);";
        System.out.println(JavaScript4J.eval(script));
    }
}
```
## 编译以调用
此方法需要在脚本后面调用方法以返回值
```java
import org.rifle.library.JavaScript4J.JavaScript4J;

import javax.script.CompiledScript;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * 方法一，传递JavaScript文件
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        CompiledScript compiledScript = JavaScript4J.compile(file);
        System.out.println(compiledScript.eval());

        /**
         * 方法二，传入代码
         */
        String script = "function test(a,b) { return a+b; } test(1,2);";
        CompiledScript compiledScript = JavaScript4J.compile(script);
        System.out.println(compiledScript.eval());
    }
}
```
## 加载以调用
```java
import org.rifle.library.JavaScript4J.JavaScript4J;
import org.rifle.library.JavaScript4J.ScriptExecutor;

import javax.script.CompiledScript;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * 方法一，传递JavaScript文件
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        ScriptExecutor executor = JavaScript4J.load(file);
        System.out.println(executor.invokeFunction("test", 1, 2));

        /**
         * 方法二，传入代码
         */
        String script = "function test(a,b) { return a+b; }";
        ScriptExecutor executor = JavaScript4J.load(file);
        System.out.println(executor.invokeFunction("test", 1, 2));
    }
}
```