package tests.api;

import api.models.CreateBuildTypeModelRequest;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class BuildConfigurationCrudTests extends BaseTest {

    @Test
    public void adminCanCreateBuildTypeWithMinFields() {

        CreateBuildTypeModelRequest buildTypeModel = CreateBuildTypeModelRequest.builder()
                .id("Test1")
                .name("BuildType1")
                .projectId("Test1")
                .description("Test BuildType version 1.0")
                .build();

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES_CREATE, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        // Сделать get запрос и убедиться что гонфиг создан
        // Сделать get запрос и что конфигурация привязана к нужному проекту
        // Созданная конфигурация присутствует в списке build configurations проекта.
        // webUrl/href заполнены корректно
    }

}
