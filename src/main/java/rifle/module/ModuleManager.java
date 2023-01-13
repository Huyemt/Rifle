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

    public ModuleBase getModule(String name) {
        return modules.getOrDefault(name, null);
    }

    public Map<String, ModuleBase> getModules() {
        return modules;
    }

    public boolean existsModule(String name) {
        return modules.containsKey(name);
    }

    public void loadModule(File file) {
        for (Pattern pattern : loader.getPluginFilters()) {
            if (!pattern.matcher(file.getName()).matches())
                continue;

            ModuleDescription description = loader.getMouduleDescription(file);
            if (description == null) {
                Rifle.getInstance().getLogger().error("Unable to load Module `{}`".replace("{}", file.getName()));
                continue;
            }

            try {
                ModuleBase moduleBase = loader.loadModule(file);
                if (moduleBase != null)
                    modules.put(description.getName(), moduleBase);
            } catch (Exception e) {
                Rifle.getInstance().getLogger().error(e.getMessage());
            }
        }
    }

    public void loadModules() {
        File f = new File(Rifle.RIFLE_PATH + "modules");
        if (!f.exists() || f.isFile())
            return;

        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                continue;
            loadModule(files[i]);
        }
    }

    public void closeModules() {
        for (ModuleBase moduleBase : modules.values())
            moduleBase.onClose();
        modules.clear();
    }
}
