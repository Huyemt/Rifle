package rifle.module;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huyemt
 */

public class ModuleDescription implements DescriptionKeys {
    private String name;
    private String main;
    private String version;
    private List<String> authors = new ArrayList<>();
    private String description;

    public ModuleDescription(String moduleName, String main, String description, String version, ArrayList<String> authors) {
        this.name = moduleName;
        this.main = main;
        this.description = description;
        this.version = version;
        this.authors = authors;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final List<String> getAuthors() {
        return authors;
    }

    public final String getMain() {
        return main;
    }

    public final String getVersion() {
        return version;
    }

    public final String getFullname() {
        return name.concat(" v").concat(version);
    }
}
