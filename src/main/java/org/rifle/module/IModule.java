package org.rifle.module;

import org.rifle.manager.CommandMap;
import org.rifle.console.logger.ModuleLogger;
import org.rifle.utils.DataFolder;

/**
 * @author Huyemt
 */

public interface IModule {
    /**
     * 当模块被加载的时候，将执行这个方法
     *
     * This method will be executed when the module is loaded
     */
    void onLoad();

    /**
     * 当模块被选用的时候，将执行这个方法
     *
     * This method will be executed when the module is selected
     */
    void onSelected();

    /**
     * 当模块被退出时，将执行这个方法（此处并非模块被卸载）
     *
     * This method will be executed when the module is exited (the module is not uninstalled here)
     */
    void onQuit();

    /**
     * 当模块被卸载，将执行这个方法
     *
     * This method will be executed when the module is uninstalled
     */
    void onDisable();

    /**
     * 获取当前模块的命令管理器
     *
     * Gets the command manager for the current module
     *
     * @return CommandMap
     */
    CommandMap getCommandMap();

    /**
     * 获取当前模块的描述
     *
     * Gets the description of the current module
     *
     * @return ModuleDescription
     */
    ModuleDescription getModuleDescription();

    /**
     * 获取当前模块的日志输出类
     *
     * Get the log output class of the current module
     *
     * @return MainLogger
     */
    ModuleLogger getLogger();

    /**
     * 获取当前模块的数据文件夹
     *
     * Get the data folder of the current module
     *
     * @return DataFolder
     */
    DataFolder getDataFolder();

    /**
     * 该模块是否被选用
     *
     * Is this module selected
     *
     * @return boolean
     */
    boolean isSelected();

    void setSelected(boolean selected);
}
