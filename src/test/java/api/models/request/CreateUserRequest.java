package api.models.request;

import api.configs.Config;
import api.generators.GeneratingRule;
import api.models.BaseModel;
import api.models.Groups;
import api.models.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest extends BaseModel {
    @GeneratingRule(regex = "^[a-z]{3,15}$")
    private String username;
    @GeneratingRule(regex = "^[a-z]{3,15}")
    private String password;
    @GeneratingRule(regex = "^[a-z]{3,10}@[a-z]{3,7}\\.[a-z]{2,3}")
    private String email;
    @GeneratingRule(regex = "^[A-Z][a-z]{2,10} [A-Z][a-z]{2,10}$")
    private String name;

    public static CreateUserRequest getAdmin() {
        return CreateUserRequest.builder()
                                .username(Config.getProperty("admin.username"))
                                .password(Config.getProperty("admin.password"))
                                .build();
    }
}
