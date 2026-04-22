package tests.api;

import api.generators.RandomModelGenerator;
import api.models.comparison.ModelAssertions;
import api.models.request.CreateUserRequest;
import api.models.response.CreateUserResponse;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CreateUserTests extends BaseTest {

    static Stream<Arguments> userFieldSets() {
        return Stream.of(
                Arguments.of("min fields: username, password",
                        new String[]{"username", "password"}),
                Arguments.of("full details: username, password, name, email",
                        new String[]{"username", "password", "name", "email"})
        );
    }

    @ParameterizedTest(name = "Admin can create user with {0}")
    @MethodSource("userFieldSets")
    public void adminCanCreateUser(String description, String[] fields) {
        CreateUserRequest userRequest = RandomModelGenerator.generate(CreateUserRequest.class, fields);
        CreateUserResponse userResponse = new ValidatedCrudRequester<CreateUserResponse>(
                RequestSpecs.adminSpec(),
                Endpoint.USERS,
                ResponseSpecs.operationOk())
                .post(userRequest);
        ModelAssertions.assertThatModels(userRequest, userResponse).match();
    }
}
