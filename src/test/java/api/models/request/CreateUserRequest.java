package api.models.request;

import api.generators.GeneratingRule;
import api.models.*;
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
    @GeneratingRule(regex = "^[A-Z][a-z]{2,10} [A-Z][a-z]{2,10}$")
    private String name;
    @Builder.Default
    private Roles roles = Roles.builder()
            .role(List.of(Role.builder().build()))
            .build();
    @Builder.Default
    private Groups groups = Groups.builder()
            .group(List.of(Group.builder().build()))
            .build();
}
