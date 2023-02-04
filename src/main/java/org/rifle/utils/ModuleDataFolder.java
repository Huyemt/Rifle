package org.rifle.utils;

import org.rifle.Rifle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Huyemt
 */

public class ModuleDataFolder extends DataFolder {
    public final File jar;
    private final String moduleName;

    public ModuleDataFolder(File jar, String moduleName) {
        super("modules");
        this.jar = jar;
        this.moduleName = moduleName;
    }

    public final File getLibraryDir() {
        return new File(Rifle.getInstance().getDataFolder().getLibraryDir() + File.separator + moduleName + File.separator);
    }

    public final void initLibraryInLocal() {
        if (jar == null)
            return;

        try (JarFile jarFile = new JarFile(jar)) {
            File lib = getLibraryDir();
            if (!lib.exists()) {
                lib.mkdirs();
            }

            for (Iterator<JarEntry> iterator = jarFile.entries().asIterator(); iterator.hasNext();) {
                JarEntry entry = iterator.next();
                if (entry.isDirectory())
                    continue;

                if (entry.getName().contains("/") && !entry.getName().startsWith("resources/") || entry.getName().endsWith(".class"))
                    continue;

                URL url = new URL("jar:file:" + jar.getAbsolutePath() + "!/" + entry.getName());
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                connection.setDoInput(true);

                FileOutputStream stream = new FileOutputStream(new File(lib, entry.getName()));
                stream.write(Utils.readFile(connection.getInputStream()).getBytes(StandardCharsets.UTF_8));
                stream.flush();

                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Rifle.getInstance().getLogger().error(e.getMessage());
        }
    }
}
