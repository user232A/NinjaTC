package tests.api;

import api.generators.RandomModelGenerator;
import api.models.CreateUserRequest;
import api.models.UserRole;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

public class UserTests extends BaseTest {

    @Test
    public void adminCanCreateUserWithOnlyMandatoryFields() {
        CreateUserRequest userRequest = RandomModelGenerator.generateUsernameAndPassword(CreateUserRequest.class);
        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.USERS, ResponseSpecs.operationOk())
                .post(userRequest);
    }

    @Test
    public void adminCanCreateUserWithValidData() {
        CreateUserRequest userRequest = RandomModelGenerator.generate(CreateUserRequest.class);
        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.USERS, ResponseSpecs.operationOk())
                .post(userRequest);
    }

    @ParameterizedTest
    @EnumSource(UserRole.class)
    public void adminCanCreateUserWithRole(UserRole role) {
        CreateUserRequest userRequest = RandomModelGenerator.generateUsernameAndPassword(CreateUserRequest.class);
        userRequest.setRoles(CreateUserRequest.RoleAssignments.builder()
                .role(List.of(CreateUserRequest.Role.builder()
                        .roleId(role)
                        .scope("g")
                        .build()))
                .build());

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.USERS, ResponseSpecs.operationOk())
                .post(userRequest);
    }
}
