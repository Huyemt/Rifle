package rifle.module;

import rifle.command.CommandMap;
import rifle.utils.ModuleLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huyemt
 */

public abstract class Module implements ModuleBase {
    private ModuleDescription description;
    private ModuleLogger logger;
    private CommandMap commandMap;
    public boolean isSelected = false;
    public boolean isSuspened = false;
    private boolean isInitalized = false;

    @Override
    public void onLoad() {

    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onSuspended() {

    }

    @Override
    public void onQuit() {

    }

    @Override
    public void onClose() {

    }

    /**
     * Get the name of this module
     * @return String
     */
    protected abstract String getModuleName();

    /**
     * Get the version of this module
     * @return String
     */
    protected abstract String getModuleVersion();

    /**
     * Get the description of this module
     * @return String
     */
    protected String getDescription() {
        return "";
    }

    /**
     * Get the authors of this module
     * @return String[]
     */
    protected String[] getAuthors() {
        return new String[0];
    }

    @Override
    public final boolean isSelected() {
        return isSelected;
    }

    @Override
    public final boolean isSuspended() {
        return isSuspened;
    }

    @Override
    public final void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public final void setSuspended(boolean suspended) {
        isSuspened = suspended;
    }

    @Override
    public final ModuleLogger getLogger() {
        return logger;
    }

    @Override
    public final ModuleDescription getModuleDescription() {
        return description;
    }

    @Override
    public final CommandMap getCommandMap() {
        return commandMap;
    }

    public final void init(String main) {
        if (!isInitalized) {
            isInitalized = true;
            commandMap = new CommandMap();
            this.logger = new ModuleLogger(getModuleName());
            this.description = new ModuleDescription(getModuleName(), main, getDescription(), getModuleVersion(), new ArrayList<>(List.of(getAuthors())));
        }
    }
}
