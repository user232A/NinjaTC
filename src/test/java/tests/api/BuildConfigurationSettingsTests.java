package tests.api;

import api.generators.RandomModelGenerator;
import api.models.CreateBuildTypeModelRequest;
import api.models.common.PropertiesDto;
import api.models.common.PropertyDto;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class BuildConfigurationSettingsTests extends BaseTest {

    @Test
    public void adminCanCreateBuildParameter() {

        // Создать проект

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        PropertyDto propertyDto = RandomModelGenerator.generate(PropertyDto.class);

        PropertyDto expectedPropertyDto = new ValidatedCrudRequester<PropertyDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .post(propertyDto);

        PropertiesDto propertiesDto = new ValidatedCrudRequester<PropertiesDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS_GET, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .get();

        PropertyDto actualPropertiesDto = propertiesDto.getProperty().stream()
                .filter(property -> property.getName().equals(propertyDto.getName())).findAny().get();

        softly.assertThat(actualPropertiesDto.getName()).isEqualTo(expectedPropertyDto.getName());
        softly.assertThat(actualPropertiesDto.getValue()).isEqualTo(expectedPropertyDto.getValue());
    }
}
