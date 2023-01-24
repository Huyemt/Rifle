# Foreword
Since `JDK` version 1.6 and later, `Java` supports calling other scripting languages.
<br>
On this basis, this support library provides a more convenient encapsulation method for calling `JavaScript`.
<br><br>
[Read the source code](../../../src/main/java/org/rifle/library/javascript4j)
***
## Eval

```java
import org.rifle.library.javascript4j.JavaScript4J;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * Method 1: Pass JavaScript files.
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        System.out.println(JavaScript4J.eval(file));

        /**
         * Method 2: Pass in the code.
         */
        String script = "function test(a,b) { return a+b; } test(1,2);";
        System.out.println(JavaScript4J.eval(script));
    }
}
```
## Compile
This method needs to call the method after the script to return the value.

```java
import org.rifle.library.javascript4j.JavaScript4J;

import javax.script.CompiledScript;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * Method 1: Pass JavaScript files.
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        CompiledScript compiledScript = JavaScript4J.compile(file);
        System.out.println(compiledScript.eval());

        /**
         * Method 2: Pass in the code.
         */
        String script = "function test(a,b) { return a+b; } test(1,2);";
        CompiledScript compiledScript = JavaScript4J.compile(script);
        System.out.println(compiledScript.eval());
    }
}
```
## Load

```java
import org.rifle.library.javascript4j.JavaScript4J;
import org.rifle.library.javascript4j.ScriptExecutor;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        /**
         * Method 1: Pass JavaScript files.
         */
        File file = new File(Test.class.getResource("").getPath() + "test.js");
        ScriptExecutor executor = JavaScript4J.load(file);
        System.out.println(executor.invokeFunction("test", 1, 2));

        /**
         * Method 2: Pass in the code.
         */
        String script = "function test(a,b) { return a+b; }";
        ScriptExecutor executor = JavaScript4J.load(file);
        System.out.println(executor.invokeFunction("test", 1, 2));
    }
}
```