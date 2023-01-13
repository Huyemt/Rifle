package rifle.module;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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

    public ModuleDescription(String yaml) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml1 = new Yaml(dumperOptions);
        parse(yaml1.loadAs(yaml, LinkedHashMap.class));
    }

    private void parse(LinkedHashMap<String, Object> map) {
        name = String.valueOf(map.get(NAME));
        main = String.valueOf(map.get(MAIN));
        description = String.valueOf(map.getOrDefault(DESCRIPTION, "no description in here"));
        version = String.valueOf(map.get(VERSION));

        if (map.containsKey(AUTHOR)) {
            String a = String.valueOf(map.get(AUTHOR));

            if (a.contains(","))
                Collections.addAll(authors, a.split(","));
            else
                authors.add(a);
        }
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
