package api.models.request;

import api.generators.GeneratingRule;
import api.models.BaseModel;
import api.models.Project;
import api.models.common.PropertiesDto;
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
public class CreateVcsRootRequest extends BaseModel {
    private String name;
    private String vcsName;
    private Project project;
    private PropertiesDto properties;
}
