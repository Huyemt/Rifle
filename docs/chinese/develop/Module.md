## [返回主开发文档](../start.md)
***
## 须知
<kbd>Rifle</kbd>依靠`Jar`内`META-INF`文件夹中的`MANIFEST.MF`文件属性`Main-Class`寻找模块主类，所以需要您手动配置主类在`Jar`中的路径。
## 例子

```java
import org.rifle.module.Module;
import org.rifle.utils.TextFormat;

public class SimpleModule extends Module {
    // 一个最基础的模块必须继承 Module 类


    @Override
    protected String getModuleName() {
        // 模块名称
        return "SimpleModule";
    }

    @Override
    protected String getModuleVersion() {
        // 模块版本号
        return "1.0.0";
    }

    @Override
    protected String[] getModuleAuthors() {
        // 模块作者
        return new String[]{"Huyemt"};
    }

    // 选填
    @Override
    protected String getModuleStringDescription() {
        // 模块描述
        return "这是一个最基础的Rifle模块例子";
    }

    // 选填
    @Override
    protected String getModuleWebsite() {
        // 相关网站
        return "http://localhost:8080";
    }

    // 选填
    @Override
    public boolean isUserCanSelect() {
        return super.isUserCanSelect();
    }

    // 选填
    @Override
    public void onLoad() {
        getLogger().info(getModuleName() + ": Hello, World");
        // 如果你想加点色彩, 可以使用 TextFormat 类
        getLogger().info(getModuleName() + ":" + TextFormat.FONT_GREEN + "Hello, World");
    }

    // 选填
    @Override
    public void onSelected() {
        // 被用户选用
        super.onSelected();
    }

    // 选填
    @Override
    public void onQuit() {
        // 被用户取消选用
        super.onQuit();
    }

    // 选填
    @Override
    public void onDisable() {
        // 模块被卸载
        super.onDisable();
    }
}
```
## 理念
1. 一个标准模块必须继承<kbd>[Module](../../../src/main/java/org/rifle/module/Module.java)</kbd>类。
2. 一个模块被加载，不代表它一定会被选用。
3. 通常情况下，一个模块被取消选用，并不意味它被卸载。取消和卸载的概念不同。