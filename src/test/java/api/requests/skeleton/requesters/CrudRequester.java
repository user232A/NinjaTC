package api.requests.skeleton.requesters;

import api.models.BaseModel;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.HTTPRequest;
import api.requests.skeleton.interfaces.CrudEndpointInterface;
import api.requests.skeleton.interfaces.GetAllEndpointInterface;
import api.requests.skeleton.interfaces.PathParamInterface;
import api.requests.skeleton.interfaces.QueryParamInterface;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CrudRequester extends HTTPRequest implements CrudEndpointInterface, PathParamInterface, QueryParamInterface, GetAllEndpointInterface {
    public CrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
        super(requestSpecification, endpoint, responseSpecification);
    }

    @Override
    public ValidatableResponse post(BaseModel model) {
        var requestBody = model == null ? "" : model;
        RequestSpecification reqSpec = given().spec(requestSpecification);

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            reqSpec.pathParam(entry.getKey(), entry.getValue());
        }

        return reqSpec
                .body(requestBody)
                .post(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse get() {
        RequestSpecification reqSpec = given().spec(requestSpecification);

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            reqSpec.pathParam(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            reqSpec.queryParam(entry.getKey(), entry.getValue());
        }

        return reqSpec
                .get(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse put(BaseModel model) {
        var requestBody = model == null ? "" : model;
        RequestSpecification reqSpec = given().spec(requestSpecification);

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            reqSpec.pathParam(entry.getKey(), entry.getValue());
        }

        return reqSpec
                .body(requestBody)
                .put(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Override
    public void put(String text) {
        RequestSpecification reqSpec = given().spec(requestSpecification);

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            reqSpec.pathParam(entry.getKey(), entry.getValue());
        }

        reqSpec
                .body(text)
                .put(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Override
    public Object delete(int id) {
        return null;
    }

    @Override
    public CrudRequester withPathParam(String param, String value) {
        this.pathParams.put(param, value);
        return this;
    }

    @Override
    public CrudRequester withQueryParam(String param, String value) {
        this.queryParams.put(param, value);
        return this;
    }

    @Override
    public ValidatableResponse getAll(Class<?> clazz) {
        RequestSpecification reqSpec = given().spec(requestSpecification);

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            reqSpec.pathParam(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            reqSpec.queryParam(entry.getKey(), entry.getValue());
        }

        return reqSpec.get(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }
}
