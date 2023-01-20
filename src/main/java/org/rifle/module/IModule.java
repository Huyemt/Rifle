package org.rifle.module;

import org.rifle.manager.CommandMap;
import org.rifle.console.logger.ModuleLogger;
import org.rifle.utils.DataFolder;

/**
 * @author Huyemt
 */

public interface IModule {
    void onLoad();

    void onSelected();

    void onQuit();

    void onDisable();

    CommandMap getCommandMap();

    ModuleDescription getModuleDescription();

    ModuleLogger getLogger();

    DataFolder getDataFolder();

    boolean isSelected();

    void setSelected(boolean selected);
}
