package rifle.module;

import rifle.Rifle;

import java.io.File;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class JarLoader implements ModuleLoader {
    private final HashMap<String, Class<?>> classHashMap = new HashMap<>();
    private final HashMap<String, ModuleClassLoader> loaderHashMap = new HashMap<>();

    public JarLoader() {

    }

    @Override
    public ModuleBase loadModule(File file) {
        // Does Rifle support the module of this file
        for (Pattern pattern : getPluginFilters())
            if (!pattern.matcher(file.getName()).matches())
                return null;

        // Get the main classpath of the module
        try (JarFile jarFile = new JarFile(file)) {
            Attributes attributes = jarFile.getManifest().getMainAttributes();
            String mainClassPath = attributes.getValue("Main-Class");

            // Does MANIFEST.MF contain the value of Main-Class
            if (mainClassPath == null)
                throw new ClassNotFoundException("Unable to load \"{}\" because \"Main-Class\" could not be found.".replace("{}", file.getName()));

            // loading it
            ModuleClassLoader classLoader = new ModuleClassLoader(this, getClass().getClassLoader(), file);
            Module module;
            try {
                Class<?> javaClass = classLoader.findClass(mainClassPath);

                if (!ModuleBase.class.isAssignableFrom(javaClass))
                    throw new Exception("The Module main class `" + mainClassPath + "` must extends `rifle.module.Module`!");

                Class<Module> moduleClass = (Class<Module>) javaClass.asSubclass(Module.class);
                module = moduleClass.newInstance();
                module.init(mainClassPath);
                loaderHashMap.put(module.getModuleName(), classLoader);
                module.onLoad();
                return module;
            } catch (ClassNotFoundException e) {
                Rifle.getInstance().getLogger().error("The Module main class `" + mainClassPath + "` must extends `rifle.module.Module`!");
            }
        } catch (Exception e) {
            Rifle.getInstance().getLogger().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Pattern[] getPluginFilters() {
        return new Pattern[]{Pattern.compile("^.+\\.jar$")};
    }

    public Class<?> getClassByName(String name) {
        Class<?> theClass = classHashMap.get(name);
        if (theClass != null)
            return theClass;

        /*
        Traverse the loader of each module.
         */
        for (ModuleClassLoader loader : loaderHashMap.values()) {
            try {
                theClass = loader.findClass(name, false);
            } catch (ClassNotFoundException e) {
                // ignore
            }

            if (theClass != null)
                return theClass;
        }

        return null;
    }

    public void setClass(final String name, final Class<?> clazz) {
        if (!classHashMap.containsKey(name))
            classHashMap.put(name, clazz);
    }

    private void removeClass(String name) {
        classHashMap.remove(name);
    }
}
