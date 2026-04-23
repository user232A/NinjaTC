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
public class Group extends BaseModel {
    @Builder.Default
    private GroupKey key = GroupKey.ALL_USERS_GROUP;
    private String name;
    private String href;
    private String description;
}