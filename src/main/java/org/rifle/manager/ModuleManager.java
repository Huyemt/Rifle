package org.rifle.manager;

import org.rifle.Rifle;
import org.rifle.module.IModule;
import org.rifle.module.ModuleLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class ModuleManager {
    private final Map<String, IModule> modules;
    private final ModuleLoader loader;

    public ModuleManager() {
        modules = new HashMap<>();
        loader = new ModuleLoader();
    }

    public final boolean exists(String name) {
        return modules.containsKey(name.toLowerCase());
    }

    public final IModule get(String name) {
        return modules.getOrDefault(name.toLowerCase(), null);
    }

    public synchronized final boolean load(File file) {
        IModule module = loader.loadModule(file);
        if (module == null)
            return false;

        if (exists(module.getModuleDescription().getName())) {
            Rifle.getInstance().getLogger().error("The module is already loaded or the name of the module conflicts: " + module.getModuleDescription().getMain());
            return false;
        }

        modules.put(module.getModuleDescription().getName().toLowerCase(), module);
        return true;
    }

    public synchronized final boolean unload(IModule module) {
        if (!exists(module.getModuleDescription().getName()))
            return false;

        loader.unloadModule(module);
        modules.remove(module.getModuleDescription().getName().toLowerCase());
        return true;
    }

    public synchronized final void loadModules() {
        File f = new File(Rifle.getInstance().getDataFolder() + "modules");
        if (f.exists() && !f.isFile()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile())
                        load(file);
                }
            }
        }
    }

    public synchronized final void unloadModules() {
        for (IModule module : modules.values()) {
            unload(module);
        }
    }

    public final IModule[] getModules() {
        return modules.values().toArray(IModule[]::new);
    }

    public final ModuleLoader getModuleLoader() {
        return loader;
    }
}
