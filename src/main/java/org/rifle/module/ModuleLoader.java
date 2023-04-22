package org.rifle.module;

import org.bullet.CompiledBullet;
import org.bullet.base.types.BtArray;
import org.bullet.interpreter.BulletRuntime;

import java.io.File;
import java.util.ArrayList;
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
    public IModule loadModule(File file) throws Exception {
        Pattern[] patterns = getFilters();
        String success = null;
        for (Pattern pattern : patterns) {
            if (pattern.matcher(file.getName()).matches())
                success = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        }

        if (success == null)
            return null;


        switch (success) {
            case "jar": {
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
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(String.format("The jar file with the path \"%s\" is corrupted", file.getAbsolutePath()));
                }
            }

            // Bullet模块
            case "bt":
            default:
                BulletModule module;
                BulletRuntime runtime = new BulletRuntime();
                CompiledBullet bullet = new CompiledBullet(file, runtime);
                bullet.eval(); // 编译计算

                String description = bullet.existsAttribute("description") ? bullet.findAttribute("description").toString() : null;
                String website = bullet.existsAttribute("website") ? bullet.findAttribute("website").toString() : null;

                module = new BulletModule(bullet.findAttribute("name").toString(), bullet.findAttribute("version").toString(), makeAus(bullet.findAttribute("authors")), description, website, (Boolean) bullet.findAttribute("canBeSelect"), bullet);
                module.init(file.getAbsolutePath(), file);
                module.onLoad();
                loaders.put(module.getModuleName(), null);
                return module;
        }
    }

    @Override
    public boolean unloadModule(IModule module) {
        if (!loaders.containsKey(module.getModuleDescription().getName()))
            return false;

        module.onDisable();

        if (loaders.get(module.getModuleDescription().getName()) != null) {
            for (String name : loaders.get(module.getModuleDescription().getName()).getClasses().keySet())
                removeClass(name);
        }
        loaders.remove(module.getModuleDescription().getName());
        return true;
    }


    public Pattern[] getFilters() {
        return new Pattern[]{
                Pattern.compile("^.+\\.jar$"),
                Pattern.compile("^.+\\.bt$")
        };
    }

    public Class<?> getClassByName(String name) {
        Class<?> theClass = this.classes.get(name);
        if (theClass != null)
            return theClass;
        else {
            for (JavaClassLoader loader : loaders.values()) {
                if (loader == null) continue;

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

    private String[] makeAus(Object r) {
        if (r instanceof BtArray) {
            ArrayList<String> arrayList = new ArrayList<>();

            for (Object rr : ((BtArray) r).vector) {
                arrayList.add(rr.toString());
            }

            return arrayList.toArray(String[]::new);
        }

        return new String[]{r.toString()};
    }
}
