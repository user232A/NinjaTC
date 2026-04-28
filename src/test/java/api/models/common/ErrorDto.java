package api.models.common;

import api.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto extends BaseModel {
    private String additionalMessage;
    private String statusText;
    private String stackTrace;
    private String message;
}
