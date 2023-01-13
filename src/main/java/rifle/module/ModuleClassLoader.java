package rifle.module;

import rifle.Rifle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Huyemt
 */

public class ModuleClassLoader extends URLClassLoader {
    private final JarLoader global;
    private final HashMap<String, Class<?>> classHashMap = new HashMap<>();

    public ModuleClassLoader(JarLoader loader, ClassLoader classLoader, File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, classLoader);
        this.global = loader;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean onGlobal) throws ClassNotFoundException {
        Class<?> result = classHashMap.get(name);
        if (result == null) {
            if (onGlobal)
                result = global.getClassByName(name);

            if (result == null) {
                result = super.findClass(name);
                if (result != null)
                    global.setClass(name, result);
            }
        }
        classHashMap.put(name, result);
        return result;
    }

    public Set<String> getClasses() {
        return classHashMap.keySet();
    }
}
