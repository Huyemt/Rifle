package org.rifle.module;

import org.rifle.manager.CommandManager;
import org.rifle.console.logger.ModuleLogger;
import org.rifle.utils.ModuleDataFolder;

import java.io.File;

/**
 * @author Huyemt
 */

public abstract class Module implements IModule {
    private final CommandManager commandManager = new CommandManager();
    private ModuleDescription moduleDescription;
    private ModuleLogger logger;
    private ModuleDataFolder dataFolder;
    private boolean initialized = false;
    protected boolean selected = false;

    @Override
    public void onLoad() {

    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onQuit() {

    }

    @Override
    public void onDisable() {

    }

    protected abstract String getModuleName();
    protected abstract String getModuleVersion();
    protected abstract String[] getModuleAuthors();

    protected String getModuleWebsite() {
        return "~";
    }

    protected String getModuleStringDescription() {
        return "~";
    }

    public void init(String main, File file) {
        if (initialized)
            return;
        String name = getModuleName().trim().replace("  ", " ").replace(" ", "_");

        initialized = true;
        moduleDescription = new ModuleDescription(main, name, getModuleVersion(), getModuleWebsite(), getModuleStringDescription(), getModuleAuthors());
        logger = new ModuleLogger(name);
        dataFolder = new ModuleDataFolder(file, getModuleDescription().getName());
    }

    @Override
    public final CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public final ModuleDescription getModuleDescription() {
        return moduleDescription;
    }

    @Override
    public ModuleLogger getLogger() {
        return logger;
    }

    @Override
    public final ModuleDataFolder getDataFolder() {
        return dataFolder;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public final void setSelected(boolean selected) {
        if (selected == isSelected())
            return;

        this.selected = selected;
        if (selected)
            onSelected();
        else
            onQuit();
    }

    @Override
    public boolean isUserCanSelect() {
        return true;
    }

    @Override
    public String toString() {
        return getModuleName();
    }
}
