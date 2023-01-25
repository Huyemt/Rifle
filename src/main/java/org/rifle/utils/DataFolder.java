package org.rifle.utils;

import java.io.File;

/**
 * @author Huyemt
 */

public class DataFolder {
    protected final File main;

    public DataFolder() {
        main = new File(System.getProperty("user.dir"));
    }

    public DataFolder(String name) {
        main = new File(System.getProperty("user.dir") + File.separator + name);
    }

    public final File getMain() {
        return main;
    }

    public final String getMainPath() {
        return main.getAbsolutePath() + File.separator;
    }

    @Override
    public final String toString() {
        return getMainPath();
    }
}
