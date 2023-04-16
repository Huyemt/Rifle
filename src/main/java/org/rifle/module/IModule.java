package org.rifle.module;

import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.UnderfineException;
import org.rifle.manager.CommandManager;
import org.rifle.console.logger.ModuleLogger;
import org.rifle.utils.DataFolder;

/**
 * @author Huyemt
 */

public interface IModule {
    /**
     * 当模块被加载的时候，将执行这个方法<br><br>
     *
     * This method will be executed when the module is loaded
     */
    void onLoad();

    /**
     * 当模块被选用的时候，将执行这个方法<br><br>
     *
     * This method will be executed when the module is selected
     */
    void onSelected();

    /**
     * 当模块被退出时，将执行这个方法（此处并非模块被卸载）<br><br>
     *
     * This method will be executed when the module is exited (the module is not uninstalled here)
     */
    void onQuit();

    /**
     * 当模块被卸载，将执行这个方法<br><br>
     *
     * This method will be executed when the module is uninstalled
     */
    void onDisable();

    /**
     * 获取当前模块的命令管理器<br><br>
     *
     * Gets the command manager for the current module
     *
     * @return CommandMap
     */
    CommandManager getCommandManager();

    /**
     * 获取当前模块的描述<br><br>
     *
     * Gets the description of the current module
     *
     * @return ModuleDescription
     */
    ModuleDescription getModuleDescription();

    /**
     * 获取当前模块的日志输出类<br><br>
     *
     * Get the log output class of the current module
     *
     * @return MainLogger
     */
    ModuleLogger getLogger();

    /**
     * 获取当前模块的数据文件夹<br><br>
     *
     * Get the data folder of the current module
     *
     * @return DataFolder
     */
    DataFolder getDataFolder();

    /**
     * 该模块是否被选用<br><br>
     *
     * Is this module selected
     *
     * @return boolean
     */
    boolean isSelected();

    /**
     * 该模块是否可以被用户选中<br><br>
     *
     * Whether the module can be selected by the user
     */
    boolean isUserCanSelect();

    void setSelected(boolean selected);
}
