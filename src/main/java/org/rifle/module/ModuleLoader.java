package org.rifle.module;

import org.rifle.Rifle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class ModuleLoader implements ILoader {
    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Map<String, JavaClassLoader> loaders = new HashMap<>();

    @Override
    public IModule loadModule(File file) {
        Pattern[] patterns = getFilters();
        String success = null;
        for (Pattern pattern : patterns) {
            if (pattern.matcher(file.getName()).matches())
                success = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        }

        if (success == null)
            return null;

        try (JarFile jarFile = new JarFile(file)) {
            Module module;
            Attributes attributes = jarFile.getManifest().getMainAttributes();
            String main = attributes.getValue("Main-Class");

            if (main == null)
                throw new Exception("Unable to load \"" + file.getName() + "\" because \"Main-Class\" could not be found.");

            JavaClassLoader loader = new JavaClassLoader(this, getClass().getClassLoader(), file);
            Class<?> clazz = loader.findClass(main);

            if (clazz.isAssignableFrom(Module.class))
                throw new Exception("The Module main class `" + main + "` must extends `rifle.module.Module`");

            Class<Module> moduleClass = (Class<Module>) clazz.asSubclass(Module.class);
            module = moduleClass.getConstructor().newInstance();
            module.init(main, file);
            module.getDataFolder().initLibraryInLocal();
            module.onLoad();
            loaders.put(module.getModuleName(), loader);
            return module;
        } catch (IOException e) {
            Rifle.getInstance().getLogger().error("The jar file with the path \"" + file.getAbsolutePath() + "\" is corrupted.");
        } catch (Exception e) {
            Rifle.getInstance().getLogger().error(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean unloadModule(IModule module) {
        if (!loaders.containsKey(module.getModuleDescription().getName()))
            return false;

        module.onDisable();

        for (String name : loaders.get(module.getModuleDescription().getName()).getClasses().keySet())
            removeClass(name);

        loaders.remove(module.getModuleDescription().getName());
        return true;
    }


    public Pattern[] getFilters() {
        return new Pattern[]{
                Pattern.compile("^.+\\.jar$")
        };
    }

    public Class<?> getClassByName(String name) {
        Class<?> theClass = this.classes.get(name);
        if (theClass != null)
            return theClass;
        else {
            for (JavaClassLoader loader : loaders.values()) {
                try {
                    theClass = loader.findClass(name, false);
                } catch (ClassNotFoundException e) {
                    continue;
                }

                if (theClass != null)
                    return theClass;
            }
        }
        return null;
    }

    public void setClass(String name, Class<?> clazz) {
        if (!classes.containsKey(name))
            classes.put(name, clazz);
    }

    public void removeClass(String name) {
        classes.remove(name);
    }

    public final JavaClassLoader getClassLoader(String name) {
        return loaders.getOrDefault(name, null);
    }
}
