package api.requests.skeleton;

import api.models.BaseModel;
import api.models.CreateBuildTypeModelRequest;
import api.models.CreateBuildTypeModelResponse;
import api.models.request.CreateUserRequest;
import api.models.response.CreateUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum Endpoint {

    USERS(
            "/app/rest/users",
            CreateUserRequest.class,
            CreateUserResponse.class,
            Map.of()
    ),

    BUILD_TYPES(
            "/app/rest/buildTypes",
            CreateBuildTypeModelRequest.class,
            CreateBuildTypeModelResponse.class,
            Map.of()
    ),

    BUILD_TYPES_GET(
            "/app/rest/buildTypes/{btLocator}",
            BaseModel.class,
            CreateBuildTypeModelResponse.class,
            Map.of("btLocator", String.class)
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
    private final Map<String, Class<String>> pathParam;
}
