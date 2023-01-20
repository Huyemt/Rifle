package org.rifle.module;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class JavaClassLoader extends URLClassLoader {
    private final ModuleLoader global;
    private final Map<String, Class<?>> classes = new HashMap<>();

    public JavaClassLoader(ModuleLoader global, ClassLoader classLoader, File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, classLoader);
        this.global = global;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean global) throws ClassNotFoundException {
        Class<?> clazz = classes.getOrDefault(name, null);

        if (clazz == null) {
            if (global)
                clazz = this.global.getClassByName(name);

            if (clazz == null) {
                clazz = super.findClass(name);
                if (clazz != null)
                    this.global.setClass(name, clazz);
            }
        }

        classes.put(name, clazz);
        return clazz;
    }

    public final Map<String, Class<?>> getClasses() {
        return classes;
    }
}
