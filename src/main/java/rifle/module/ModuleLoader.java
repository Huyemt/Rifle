package rifle.module;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public interface ModuleLoader {

    ModuleBase loadModule(File file) throws Exception;

    ModuleDescription getMouduleDescription(File file);

    Pattern[] getPluginFilters();
}
