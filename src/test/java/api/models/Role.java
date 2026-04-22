package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseModel {
    @Builder.Default
    private UserRole roleId = UserRole.SYSTEM_ADMIN;
    @Builder.Default
    private String scope = "g";
}