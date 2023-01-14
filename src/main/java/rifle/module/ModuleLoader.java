package rifle.module;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public interface ModuleLoader {

    /**
     * Loading a module by class "File"
     * @param file
     * @return ModuleBase
     * @throws Exception
     */
    ModuleBase loadModule(File file) throws Exception;

    /**
     * Gets Patterns that determine if the file type matches the module file type
     *
     * This method created for other language modules
     * @return Pattern[]
     */
    Pattern[] getPluginFilters();
}
