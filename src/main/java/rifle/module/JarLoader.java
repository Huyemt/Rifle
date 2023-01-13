package rifle.module;

import rifle.Rifle;
import rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @author Huyemt
 */

public class JarLoader implements ModuleLoader {
    private final HashMap<String, Class> classHashMap = new HashMap<>();
    private final HashMap<String, ModuleClassLoader> loaderHashMap = new HashMap<>();

    public JarLoader() {

    }


    @Override
    public ModuleBase loadModule(File file) throws Exception {
        ModuleDescription description = getMouduleDescription(file);
        if (description == null)
            return null;

        // get main classpath
        String mainClassPath = description.getMain();
        ModuleClassLoader classLoader = new ModuleClassLoader(this, getClass().getClassLoader(), file);
        loaderHashMap.put(description.getName(), classLoader);
        Module module;
        try {
            Class<?> javaClass = classLoader.findClass(mainClassPath);

            if (!ModuleBase.class.isAssignableFrom(javaClass))
                throw new Exception("The Module main class `" + description.getMain() + "` must extends `rifle.module.Module`!");

            Class<Module> moduleClass = (Class<Module>) javaClass.asSubclass(Module.class);
            module = moduleClass.newInstance();
            module.init(description);
            module.onLoad();
            return module;
        } catch (ClassNotFoundException e) {
            throw new Exception("The Module main class `" + description.getMain() + "` is not found!");
        }
    }


    @Override
    public ModuleDescription getMouduleDescription(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            // finding module.yml
            JarEntry entry = jarFile.getJarEntry("module.yml");
            if (entry == null)
                return null;

            try (InputStream stream = jarFile.getInputStream(entry)) {
                return new ModuleDescription(Utils.readFile(stream));
            }
        } catch (IOException e) {
            return null;
        }
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
