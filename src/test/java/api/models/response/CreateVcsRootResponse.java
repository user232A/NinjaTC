package api.models.response;

import api.models.BaseModel;
import api.models.Project;
import api.models.VcsRootInstances;
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
public class CreateVcsRootResponse extends BaseModel {
    private String id;
    private String name;
    private String vcsName;
    private String href;
    private Project project;
    private PropertiesDto properties;
    private VcsRootInstances vcsRootInstances;
}