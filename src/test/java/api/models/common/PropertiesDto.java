package api.models.common;

import api.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesDto extends BaseModel {
    private List<PropertyDto> property;
    private int count;
    private String href;
}