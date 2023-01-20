package org.rifle.utils;

import java.io.File;

/**
 * @author Huyemt
 */

public class DataFolder {
    private final String mainPath;

    public DataFolder(String mainPath) {
        this.mainPath = mainPath;
    }

    public final String getMainPath() {
        return mainPath;
    }

    @Override
    public final String toString() {
        return mainPath + File.separator;
    }

    public final File toFile() {
        return new File(getMainPath());
    }
}
