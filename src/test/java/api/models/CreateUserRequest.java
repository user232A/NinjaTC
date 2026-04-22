package api.models;

import api.configs.Config;
import api.generators.GeneratingRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest extends BaseModel {
  @GeneratingRule(regex = "^[a-z]{3,15}$")
  private String username;
  @GeneratingRule(regex = "^[a-z]{3,15}")
  private String password;
  @GeneratingRule(regex = "^[a-z]{3,10}@[a-z]{3,7}\\.[a-z]{2,3}")
  private String email;
  private RoleAssignments roles;
  private Groups groups;

  public static CreateUserRequest getAdmin() {
    return CreateUserRequest.builder()
                            .username(Config.getProperty("admin.username"))
                            .password(Config.getProperty("admin.password"))
                            .build();
  }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoleAssignments {
        private List<Role> role;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Role {
        private UserRole roleId = UserRole.SYSTEM_ADMIN;
        private String scope = "g";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Groups {
        private List<Group> group;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Group {
        private String key = "ALL_USERS_GROUP";
    }
}