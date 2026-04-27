package tests.api;

import api.generators.RandomModelGenerator;
import api.models.CreateBuildTypeModelRequest;
import api.models.common.ErrorResponseDto;
import api.models.common.PropertiesDto;
import api.models.common.PropertyDto;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class BuildConfigurationParameterTests extends BaseTest {

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

        PropertyDto actualPropertyDto = propertiesDto.getProperty().stream()
                .filter(property -> property.getName().equals(propertyDto.getName())).findFirst()
                .orElseThrow(() -> new AssertionError("Property with name: " + propertyDto.getName() +
                        " not found"));

        softly.assertThat(expectedPropertyDto).extracting(PropertyDto::getName, PropertyDto::getValue)
                .containsExactly(propertyDto.getName(), propertyDto.getValue());

        softly.assertThat(actualPropertyDto).extracting(PropertyDto::getName, PropertyDto::getValue)
                .containsExactly(propertyDto.getName(), propertyDto.getValue());
    }

    @Test
    public void adminCanUpdateBuildParameter() {

        // Создать проект

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        PropertyDto propertyDto = RandomModelGenerator.generate(PropertyDto.class);

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES_PARAMETERS, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .post(propertyDto);

        PropertyDto modifiedPropertyDto = RandomModelGenerator.generate(PropertyDto.class);

        modifiedPropertyDto.setName(propertyDto.getName());

        PropertyDto updatedParameter = new ValidatedCrudRequester<PropertyDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_FIELD_PARAMETERS_UPDATE,
                ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .withPathParam("name", propertyDto.getName())
                .put(modifiedPropertyDto);

        PropertiesDto propertiesDto = new ValidatedCrudRequester<PropertiesDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS_GET, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .get();

        PropertyDto actualPropertyDto = propertiesDto.getProperty().stream()
                .filter(property -> property.getName().equals(propertyDto.getName())).findFirst()
                .orElseThrow(() -> new AssertionError("Property with name: " + propertyDto.getName() +
                        " not found"));

        softly.assertThat(updatedParameter).extracting(PropertyDto::getName, PropertyDto::getValue)
                .containsExactly(modifiedPropertyDto.getName(), modifiedPropertyDto.getValue());
        softly.assertThat(actualPropertyDto).extracting(PropertyDto::getName, PropertyDto::getValue)
                .containsExactly(modifiedPropertyDto.getName(), modifiedPropertyDto.getValue());
    }

    @Test
    public void adminCreateBuildParameterWithExistingNameDoesNotCreateDuplicate() {

        CreateBuildTypeModelRequest buildTypeModel = RandomModelGenerator
                .generate(CreateBuildTypeModelRequest.class, "id", "name", "projectId", "description");

        // впоследствии projectId будем брать из созданного проекта
        buildTypeModel.setProjectId("Test1");

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES, ResponseSpecs.operationOk())
                .post(buildTypeModel);

        PropertyDto propertyDto = RandomModelGenerator.generate(PropertyDto.class);

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES_PARAMETERS, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .post(propertyDto);

        PropertiesDto initialPropertiesDto = new ValidatedCrudRequester<PropertiesDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS_GET, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .get();

        new CrudRequester(RequestSpecs.adminSpec(), Endpoint.BUILD_TYPES_PARAMETERS, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .post(propertyDto);

        PropertiesDto actualPropertiesDto = new ValidatedCrudRequester<PropertiesDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS_GET, ResponseSpecs.operationOk())
                .withPathParam("btLocator", "id:" + buildTypeModel.getId())
                .get();

        long countProperty = actualPropertiesDto.getProperty().stream()
                .filter(property -> property.getName().equals(propertyDto.getName())).count();

        softly.assertThat(initialPropertiesDto.getProperty().size()).isEqualTo(actualPropertiesDto.getProperty().size());
        softly.assertThat(countProperty).isEqualTo(1);
    }

    @Test
    public void adminCanNotCreateBuildParameterInNonExistingBuildType() {

        PropertyDto propertyDto = RandomModelGenerator.generate(PropertyDto.class);

        ErrorResponseDto errors = new ValidatedCrudRequester<ErrorResponseDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_PARAMETERS, ResponseSpecs.notFound())
                .withPathParam("btLocator", "id:nonExisting")
                .postAs(propertyDto, ErrorResponseDto.class);

        softly.assertThat(errors).isNotNull();
        softly.assertThat(errors.getErrors()).isNotNull().isNotEmpty();
        softly.assertThat(errors.getErrors().getFirst().getMessage()).contains("No build type nor template is " +
                "found by id");
    }

    @Test
    public void adminCanNotUpdateBuildParameterInNonExistingBuildType() {

        PropertyDto propertyDto = RandomModelGenerator.generate(PropertyDto.class);

        ErrorResponseDto errors = new ValidatedCrudRequester<ErrorResponseDto>(RequestSpecs.adminSpec(),
                Endpoint.BUILD_TYPES_FIELD_PARAMETERS_UPDATE, ResponseSpecs.notFound())
                .withPathParam("btLocator", "id:nonExisting")
                .withPathParam("name", propertyDto.getName())
                .putAs(propertyDto, ErrorResponseDto.class);

        softly.assertThat(errors).isNotNull();
        softly.assertThat(errors.getErrors()).isNotNull().isNotEmpty();
        softly.assertThat(errors.getErrors().getFirst().getMessage()).contains("No build type nor template is " +
                "found by id");
    }
}
