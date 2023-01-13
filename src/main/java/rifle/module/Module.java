package rifle.module;

import rifle.utils.ModuleLogger;

/**
 * @author Huyemt
 */

public class Module implements ModuleBase {
    private ModuleDescription description;
    private ModuleLogger logger;

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
    public void onSuspend() {

    }

    @Override
    public void onQuit() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean isSuspended() {
        return isSuspened;
    }

    @Override
    public ModuleLogger getLogger() {
        return logger;
    }

    @Override
    public ModuleDescription getModuleDescription() {
        return description;
    }

    public final void init(ModuleDescription description) {
        if (!isInitalized) {
            isInitalized = true;
            this.description = description;
            this.logger = new ModuleLogger(description.getName());
        }
    }
}
