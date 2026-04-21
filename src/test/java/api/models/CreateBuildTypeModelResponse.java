package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBuildTypeModelResponse extends BaseModel {

    private String id;
    private String name;
    private String description;
    private String projectName;
    private String projectId;
    private String href;
    private String webUrl;
    private ProjectModel project;
}
