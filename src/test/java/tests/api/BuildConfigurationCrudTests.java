package tests.api;

import api.models.CreateBuildTypeModelRequest;
import api.models.CreateBuildTypeModelResponse;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class BuildConfigurationCrudTests extends BaseTest {

    @Test
    public void adminCanCreateBuildTypeWithMinFields() {

        CreateBuildTypeModelRequest buildTypeModel = CreateBuildTypeModelRequest.builder()
                .id("Test8")
                .name("BuildType8")
                .projectId("Test1")
                .description("Test BuildType version 8.0")
                .build();

        CreateBuildTypeModelResponse expectedBuildType = new ValidatedCrudRequester<CreateBuildTypeModelResponse>(
                RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES_CREATE, ResponseSpecs.operationOk())
                .post(buildTypeModel);


        // Сделать get запрос и убедиться что гонфиг создан (в дальнейшем заменить на SystemAdminSteps)
        ValidatedCrudRequester<CreateBuildTypeModelResponse> requester =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES_GET, ResponseSpecs.operationOk())
                        .addPathParam("btLocator", "id:" + buildTypeModel.getId());

        CreateBuildTypeModelResponse actualBuildType = requester.get();

        softly.assertThat(actualBuildType.getId()).isEqualTo(expectedBuildType.getId());
        softly.assertThat(actualBuildType.getProjectId()).isEqualTo(expectedBuildType.getProjectId());
        softly.assertThat(actualBuildType.getHref()).containsAnyOf(expectedBuildType.getId());
        softly.assertThat(actualBuildType.getWebUrl()).containsAnyOf(expectedBuildType.getId());
    }
}
