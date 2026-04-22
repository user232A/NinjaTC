package api.requests.skeleton;

import api.models.BaseModel;
import api.models.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum Endpoint {

    USERS(
            "httpAuth/app/rest/users",
            CreateUserRequest.class,
            BaseModel.class,
            Map.of()
    );

    private final String url;
    private final Class<? extends BaseModel> requestModel;
    private final Class<? extends BaseModel> responseModel;
    private final Map<String, Class<Integer>> pathParam;
}
