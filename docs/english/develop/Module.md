## [Return to the main development manual](../start.md)
***
## Notice
<kbd>Rifle</kbd> relies on the `Main-Class` attribute of `MANIFEST.MF` file in the `META-INF` folder in the `Jar` to find the main class of the module, so you need to manually configure the path of the main class in the `Jar`.
## Example
```java
import org.rifle.module.Module;
import org.rifle.utils.TextFormat;

public class SimpleModule extends Module {
    // A most basic module must extend the Module class


    @Override
    protected String getModuleName() {
        // Module name
        return "SimpleModule";
    }

    @Override
    protected String getModuleVersion() {
        // Module version BtNumber
        return "1.0.0";
    }

    @Override
    protected String[] getModuleAuthors() {
        // Module author
        return new String[]{"Huyemt"};
    }

    // Optional
    @Override
    protected String getModuleStringDescription() {
        // Module description
        return "This is a basic example of Rifle module";
    }

    // Optional
    @Override
    protected String getModuleWebsite() {
        // Related websites
        return "http://localhost:8080";
    }

    // Optional
    @Override
    public boolean isUserCanSelect() {
        return super.isUserCanSelect();
    }

    // Optional
    @Override
    public void onLoad() {
        getLogger().info(getModuleName() + ": Hello, World");
        // If you want to add some color, you can use the TextFormat class
        getLogger().info(getModuleName() + ":" + TextFormat.FONT_GREEN + "Hello, World");
    }

    // Optional
    @Override
    public void onSelected() {
        // Selected by users
        super.onSelected();
    }

    // Optional
    @Override
    public void onQuit() {
        // Quit by user
        super.onQuit();
    }
    
    // Optional
    @Override
    public void onDisable() {
        // Module uninstalled
        super.onDisable();
    }
}
```
## Idea
1. A standard module must extend the <kbd>[Module](../../../src/main/java/org/rifle/module/Module.java)</kbd> class.
2. Just because a module is loaded does not mean it will be selected.
3. Normally, a module is unselected, which does not mean it is uninstalled. The concepts of cancellation and uninstallation are different.