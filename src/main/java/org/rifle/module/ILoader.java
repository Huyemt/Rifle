package org.rifle.module;

import java.io.File;

/**
 * @author Huyemt
 */

public interface ILoader {
    IModule loadModule(File file) throws Exception;

    boolean unloadModule(IModule module);
}
