package tests.api;

import api.models.CreateUserModelRequest;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class UserTests extends BaseTest {

    @Test
    public void adminCanCreateUser() {

        CreateUserModelRequest userModelRequest = CreateUserModelRequest.builder()
                .username("test5")
                .password("232432")
                .build();

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.USERS, ResponseSpecs.operationOk())
                .post(userModelRequest);
    }
}
