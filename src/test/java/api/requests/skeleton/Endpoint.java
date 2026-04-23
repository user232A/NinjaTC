package api.requests.skeleton;

import api.models.BaseModel;
import api.models.request.CreateUserRequest;
import api.models.response.CreateUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {
    USERS(
            "/app/rest/users",
            CreateUserRequest.class,
            CreateUserResponse.class
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
}
