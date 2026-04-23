package api.models.response;

import api.models.BaseModel;
import api.models.Groups;
import api.models.Roles;
import api.models.common.PropertiesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse extends BaseModel {
    private String username;
    private int id;
    private String href;
    private PropertiesDto properties;
    private Roles roles;
    private Groups groups;
}