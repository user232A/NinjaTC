package api.models;

import api.generators.GeneratingRule;
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
public class CreateBuildTypeModelRequest extends BaseModel {

    @GeneratingRule(regex = "[A-Z][a-zA-Z0-9]{2,10}")
    private String id;

    @GeneratingRule(regex = "[A-Za-z][A-Za-z0-9 ]{4,12}")
    private String name;

    @GeneratingRule(regex = "[A-Z][a-zA-Z0-9]{2,10}")
    private String projectId;

    @GeneratingRule(regex = "[A-Za-z0-9 ,.!?]{10,20}")
    private String description;
}
