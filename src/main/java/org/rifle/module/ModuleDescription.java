package org.rifle.module;

/**
 * @author Huyemt
 */

public class ModuleDescription {
    private final String main;
    private final String name;
    private final String version;
    private final String description;
    private final String website;
    private final String[] authors;

    public ModuleDescription(String main, String name, String version, String website, String description, String[] authors) {
        this.main = main;
        this.name = name;
        this.version = version;
        this.website = website;
        this.description = description;
        this.authors = authors;
    }

    public final String getMain() {
        return main;
    }

    public final String getName() {
        return name;
    }

    public final String getVersion() {
        return version;
    }

    public String getWebsite() {
        return website;
    }

    public final String getDescription() {
        return description;
    }

    public final String[] getAuthors() {
        return authors;
    }
}
