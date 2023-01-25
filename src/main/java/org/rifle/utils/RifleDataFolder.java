package org.rifle.utils;

import java.io.File;

/**
 * @author Huyemt
 */

public class RifleDataFolder extends DataFolder {
    protected final File modules;
    protected final File libs;

    public RifleDataFolder() {
        super();

        modules = getFile("modules");
        if (modules.exists()) {
            if (modules.isFile()) {
                modules.delete();
                modules.mkdirs();
            }
        } else
            modules.mkdirs();

        libs = getFile("libs");
        if (libs.exists()) {
            if (libs.isFile()) {
                libs.delete();
                libs.mkdirs();
            }
        } else
            libs.mkdirs();
    }

    private RifleDataFolder(String name) {
        this();
    }

    public final File getFile(String name) {
        return new File(getMainPath() + name);
    }

    public final String getFilePath(String name) {
        return getMainPath() + name;
    }

    public final File getLibraryDir() {
        return libs;
    }

    public final File getModulesDir() {
        return modules;
    }
}
