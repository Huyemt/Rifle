package rifle.module;

import rifle.Rifle;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class ModuleManager {
    protected final Map<String, ModuleBase> modules = new LinkedHashMap<>();
    protected final JarLoader loader;

    public ModuleManager() {
        loader = new JarLoader();
    }

    /**
     * Using name to get a module that was loaded by Rifle
     * @param name
     * @return ModuleBase
     */
    public ModuleBase getModule(String name) {
        return modules.getOrDefault(name, null);
    }

    public Map<String, ModuleBase> getModules() {
        return modules;
    }

    public boolean existsModule(String name) {
        return modules.containsKey(name);
    }

    /**
     * Loading a module jar
     * @param file
     */
    public void loadModule(File file) {
        for (Pattern pattern : loader.getPluginFilters()) {
            if (!pattern.matcher(file.getName()).matches())
                return;
        }

        try {
            ModuleBase moduleBase = loader.loadModule(file);
            if (moduleBase != null)
                modules.put(moduleBase.getModuleDescription().getName(), moduleBase);
        } catch (Exception e) {
            Rifle.getInstance().getLogger().error(e.getMessage());
        }
    }

    /**
     * Loading modules from the dir path "modules"
     */
    public void loadModules() {
        File f = new File(Rifle.getInstance().getRiflePath() + "modules");
        if (!f.exists() || f.isFile())
            return;

        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                continue;
            loadModule(files[i]);
        }
    }

    /**
     * Unloading modules that was loaded by Rifle
     */
    public void closeModules() {
        for (ModuleBase moduleBase : modules.values())
            moduleBase.onClose();
        modules.clear();
    }
}
