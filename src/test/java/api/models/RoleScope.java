package api.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleScope {
    GLOBAL("g"),
    PROJECT("p:_Root");

    private final String value;

    RoleScope(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static String forProject(String projectId) {
        return "p:" + projectId;
    }
}