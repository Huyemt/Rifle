## [返回主开发文档](../start.md)
***
## 例子

```java
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.module.Module;

import java.util.LinkedList;

public class YourModule extends Module {
   // 省略部分代码...


   @Override
   public void onLoad() {
      getCommandManager().register(new SimpleCommand());
   }
}

class SimpleCommand extends Command {
   public SimpleCommand() {
      super("simple", "这是一个简单的命令例子", new String[]{"simple"});
      // 如果您想让命令当作任务运行，您可以使用下面的写法
      // super("simple", "这是一个简单的命令例子", new String[]{"simple"}, true);
   }

   @Override
   public void execute(Argument argument) {
      // 顺序参数
      // 它的参数是有序的
      String[] order = argument.asOrderArgument();

      // 关键字参数
      // 通过关键字获取到对应的值，每个关键字都是以 "-" 开头的。
      // -c 的关键字是 c
      // --spider-info 的关键字是 spider-info 
      // 无论 "-" 重复出现在这个关键字的字符串多少次，他的有效字段永远是 "-" 后面的内容。
      LinkedList<String> list = argument.asKeyArgument().getArgument("关键字");
   }
   
   // 当用户按下Tab键的时候，会根据命令名称调用该方法
   @Override
   public String[] complete(String reference, String[] args) {
      // reference是当前的的内容，也就是args里面最后一个值，若该值为null，那么说明args的长度是0
      // args里面不包括命令名称
      /**
       * 一个简单的例子
       * 输入如下命令
       * simple a b
       * 
       * 那么会得到如下参数
       * 
       * args = [a, b]
       * reference = b
       */
      return new String[0];
   }
}
```
当然，您也可以分开文件编写自定义命令，参考<kbd>Rifle</kbd>的[内置命令](../../../src/main/java/org/rifle/command/builtIn)。
## 理念
1. 一个自定义命令必须继承<kbd>[Command](../../../src/main/java/org/rifle/command/Command.java)</kbd>类。
2. 一个命令有以下属性:
   1. 名称
   2. 描述
   3. 使用方法
   4. 是否可以被当作任务运行在后台
3. 一个命令可以设定为能够当作任务运行在后台。
4. 一个模块可以拥有很多个命令，通常情况下，它们只能注册在模块的<kbd>[CommandManager](../../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>中，而不是注册在<kbd>Rifle</kbd>的<kbd>[CommandManager](../../../src/main/java/org/rifle/manager/CommandManager.java)</kbd>中。
5. 基本命令参数类型有[`顺序参数`](../../../src/main/java/org/rifle/command/arguments/OrderArgument.java)，[`关键字参数`](../../../src/main/java/org/rifle/command/arguments/KeyArgument.java)。
6. 您可以自定义命令参数的类型，并且通过<kbd>[Argument](../../../src/main/java/org/rifle/command/arguments/Argument.java)</kbd>的`as()`方法将参数类型转为您定义的参数类型。