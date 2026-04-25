package api.generators;

import api.configs.Config;
import api.models.Project;
import api.models.common.PropertiesDto;
import api.models.common.PropertyDto;

import java.util.List;

public class VcsRootRequestGenerator {
    public static Project defaultRootProject() {
        return Project.builder()
                .id(Config.getProperty("vcs.root.project.id"))
                .build();
    }

    public static PropertiesDto defaultProperties() {
        return PropertiesDto.builder()
                .property(List.of(
                        new PropertyDto("url", Config.getProperty("vcs.root.repository.url")),
                        new PropertyDto("branch", Config.getProperty("vcs.root.branch"))
                ))
                .build();
    }

    // уникальное имя для VSC Root
    public static String uniqueVcsRootName() {
        return Config.getProperty("vcs.root.name.prefix") + System.currentTimeMillis();
    }
}
