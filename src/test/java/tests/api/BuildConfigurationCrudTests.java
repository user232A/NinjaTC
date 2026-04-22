package tests.api;

import api.generators.RandomModelGenerator;
import api.models.BuildTypesListResponse;
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
import java.util.Random;

import java.util.List;

public class BuildConfigurationCrudTests extends BaseTest {

    @Test
    public void adminCanCreateBuildTypeWithMinFields() {

        // Создать проект

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

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
    public void adminCanUpdateBuildTypeFieldName(){

        //Позже добавлю генерацию поля
        String name = "Example2";

        // Создать проект

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

        CreateBuildTypeModelResponse initialBuildType = new ValidatedCrudRequester<CreateBuildTypeModelResponse>(
                RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        new CrudRequester(RequestSpecs.adminTextSpec(), Endpoint.BUILD_TYPES_FIELD, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .withPathParam("field", "name")
                .put(name);

        ValidatedCrudRequester<CreateBuildTypeModelResponse> requester =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES_GET, ResponseSpecs.operationOk())
                        .withPathParam("btLocator", "id:" + buildTypeModel.getId());

        CreateBuildTypeModelResponse actualBuildType = requester.get();

        softly.assertThat(actualBuildType.getName()).isEqualTo(name);
        softly.assertThat(actualBuildType.getName()).isNotEqualTo(initialBuildType.getName());
    }

    @Test
    public void unauthorizedUserCanNotCreateBuildType() {

        // Создать проект

        //в дальнейшем заменить на SystemAdminSteps
        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .withQueryParam("locator", "project:id:Test1")
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

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

        // Создать проект

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

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
    public void adminCanNotCreateBuildTypeInNonExistingProject() {

        // Создать проект

        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

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

    @Test
    public void adminCanNotCreateBuildTypeWithoutProjectId() {

        List<CreateBuildTypeModelResponse> expectedListBuildTypes =
                new ValidatedCrudRequester<CreateBuildTypeModelResponse>(RequestSpecs.adminSpec(),
                        Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                        .getAs(BuildTypesListResponse.class)
                        .getBuildTypes();

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "description");

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
