package org.rifle.module;

import java.io.File;

/**
 * @author Huyemt
 */

public interface ILoader {
    IModule loadModule(File file);

    boolean unloadModule(IModule module);
}
