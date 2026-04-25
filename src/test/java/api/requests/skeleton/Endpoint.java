package api.requests.skeleton;

import api.models.BaseModel;
import api.models.request.CreateUserRequest;
import api.models.request.CreateVcsRootRequest;
import api.models.response.CreateUserResponse;
import api.models.response.CreateVcsRootResponse;
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
    VCS_ROOTS(
            "/app/rest/vcs-roots",
            CreateVcsRootRequest.class,
            CreateVcsRootResponse.class,
            Map.of()
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
    private final Map<String, Class<String>> pathParam;
}
