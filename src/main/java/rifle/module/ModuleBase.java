package rifle.module;

import rifle.utils.ModuleLogger;

/**
 * This file defines the methods that each module has.
 *
 * @author Huyemt
 */

public interface ModuleBase {
    /**
     * When the Rifle is loading, execute this method
     */
    void onLoad();

    /**
     * When the user selects the module, execute this method
     */
    void onSelected();

    /**
     * This method is executed when the user suspends the module (not quits the module)
     */
    void onSuspend();

    /**
     * This method is executed when the user exits the module
     */
    void onQuit();

    /**
     * When the Rifle is closing, execute this method
     */
    void onClose();

    /**
     * Gets whether the module is selected
     * @return boolean
     */
    boolean isSelected();

    /**
     * Gets whether the module is suspended
     * @return boolean
     */
    boolean isSuspended();

    /**
     * Get the logger of this module
     * @return ModuleLogger
     */
    ModuleLogger getLogger();

    /**
     * Get all the information of the module
     * @return ModuleDescription
     */
    ModuleDescription getModuleDescription();
}
