package tests.api;

import api.models.BuildTypesListResponse;
import api.models.CreateBuildTypeModelRequest;
import api.models.CreateBuildTypeModelResponse;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

import java.util.List;

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
                RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        //в дальнейшем заменить на SystemAdminSteps
        ValidatedCrudRequester<CreateBuildTypeModelResponse> requester =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES_GET, ResponseSpecs.operationOk())
                        .withPathParam("btLocator", "id:" + buildTypeModel.getId());

        CreateBuildTypeModelResponse actualBuildType = requester.get();

        softly.assertThat(actualBuildType.getId()).isEqualTo(expectedBuildType.getId());
        softly.assertThat(actualBuildType.getProjectId()).isEqualTo(expectedBuildType.getProjectId());
        softly.assertThat(actualBuildType.getHref()).containsAnyOf(expectedBuildType.getId());
        softly.assertThat(actualBuildType.getWebUrl()).containsAnyOf(expectedBuildType.getId());
    }

    @Test
    public void unauthorizedUserCanNotCreateBuildType() {

        //в дальнейшем заменить на SystemAdminSteps
        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .withQueryParam("locator", "project:id:Test1")
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        CreateBuildTypeModelRequest buildTypeModel = CreateBuildTypeModelRequest.builder()
                .id("Test10")
                .name("BuildType10")
                .projectId("Test1")
                .description("Test BuildType version 10.0")
                .build();

        new CrudRequester(RequestSpecs.unAuthSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.unAuthorizedUser())
                .post(buildTypeModel);

        //в дальнейшем заменить на SystemAdminSteps
        List<CreateBuildTypeModelResponse> actualListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .withQueryParam("locator", "project:id:Test1")
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        List<String> expectedIds = expectedListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        List<String> actualIds = actualListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        softly.assertThat(actualListBuildTypes.size()).isEqualTo(expectedListBuildTypes.size());
        softly.assertThat(actualIds).containsExactlyInAnyOrderElementsOf(expectedIds);
        softly.assertThat(actualIds).doesNotContain(buildTypeModel.getId());
    }

    @Test
    public void adminCanNotCreateBuildTypeWithDuplicateId() {

        CreateBuildTypeModelRequest buildTypeModel = CreateBuildTypeModelRequest.builder()
                .id("Test15")
                .name("BuildType15")
                .projectId("Test1")
                .description("Test BuildType version 15.0")
                .build();

        new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES,
                ResponseSpecs.operationOk())
                .post(buildTypeModel);

        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .withQueryParam("locator", "project:id:Test1")
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.badRequest())
                .post(buildTypeModel);

        List<CreateBuildTypeModelResponse> actualListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .withQueryParam("locator", "project:id:Test1")
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        List<String> expectedIds = expectedListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        List<String> actualIds = actualListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        softly.assertThat(actualListBuildTypes.size()).isEqualTo(expectedListBuildTypes.size());
        softly.assertThat(actualIds).containsExactlyInAnyOrderElementsOf(expectedIds);
    }

    @Test
    public void canNotCreateBuildTypeInNonExistingProject() {

        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        CreateBuildTypeModelRequest buildTypeModel = CreateBuildTypeModelRequest.builder()
                .id("Test14")
                .name("BuildType14")
                .projectId("FakeProject")
                .description("Test BuildType version 14.0")
                .build();

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.badRequest())
                .post(buildTypeModel);

        List<CreateBuildTypeModelResponse> actualListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        List<String> expectedIds = expectedListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        List<String> actualIds = actualListBuildTypes.stream()
                .map(CreateBuildTypeModelResponse::getId)
                .toList();

        softly.assertThat(actualListBuildTypes.size()).isEqualTo(expectedListBuildTypes.size());
        softly.assertThat(actualIds).containsExactlyInAnyOrderElementsOf(expectedIds);
        softly.assertThat(actualIds).doesNotContain(buildTypeModel.getId());
    }
}
