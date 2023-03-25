## [Return to the main development manual](../start.md)
***
## Example
```java
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.module.Module;

import java.util.LinkedList;

public class YourModule extends Module {
    // Omit part of the code...


    @Override
    public void onLoad() {
        getCommandManager().register(new SimpleCommand());
    }
}

class SimpleCommand extends Command {
    public SimpleCommand() {
        super("simple", "This is a simple command example", new String[]{"simple"});
        // If you want the command to run as a task, you can use the following method
        // super("simple", "This is a simple command example", new String[]{"simple"}, true);
    }

    @Override
    public void execute(Argument argument) {
        // Sequence parameter
        // Its parameters are ordered
        String[] order = argument.asOrderArgument();

        // Keyword parameter
        // Get the corresponding value through keywords. Each keyword starts with "-".
        // The keyword of "-c" is "c"
        // The keyword of "--spider-info" is "spider-info"
        // No matter how many times "-" repeatedly appears in the string of this keyword, its valid field is always the content after "-".
        LinkedList<String> list = argument.asKeyArgument().getArgument("key");
    }

   // When the user presses the Tab key, this method is called based on the command name
   @Override
   public String[] complete(String reference, String[] args) {
      // `reference` is the current content, which is the last value in `args`. If the value is null, then the length of `args` is 0
      // `args` does not contain command names
      /**
       * A simple example
       * Enter the following command:
       * "simple a b"
       *
       * So you get the following parameters:
       *
       * args = [a, b]
       * reference = b
       */
      return new String[0];
   }
}
```
Of course, you can also write custom commands in separate files, referring to [Rifle's built-in commands](../../../src/main/java/org/rifle/command/builtIn).
## Idea
1. A custom command must extend the <kbd>[Command](../../../src/main/java/org/rifle/command/Command.java)</kbd> class.
2. A command has the following properties:
   1. name
   2. description
   3. usages
   4. whether it can be as a task
3. A command can be set to run in the background as a task.
4. A module can have many commands. Generally, they can only be registered in the <kbd>[CommandManager](../../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> of the module, not in the <kbd>[CommandManager](../../../src/main/java/org/rifle/manager/CommandManager.java)</kbd> of <kbd>Rifle</kbd>.
5. Basic command parameter types include [`OrderArgument`](../../../src/main/java/org/rifle/command/arguments/OrderArgument.java) and [`KeyArgument`](../../../src/main/java/org/rifle/command/arguments/KeyArgument.java).
6. You can customize the type of command parameters, and convert the parameter type to the parameter type you defined through the `as()` method of <kbd>[Argument](../../../src/main/java/org/rifle/command/arguments/Argument.java)</kbd>.