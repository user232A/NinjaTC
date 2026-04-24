package api.models.common;

import api.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertiesDto extends BaseModel {
    private List<PropertyDto> property;
    private Integer count;
    private String href;
}