package api.requests.skeleton.requesters;

import api.models.BaseModel;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.HTTPRequest;
import api.requests.skeleton.interfaces.CrudEndpointInterface;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class CrudRequester extends HTTPRequest implements CrudEndpointInterface {
    public CrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
        super(requestSpecification, endpoint, responseSpecification);
    }

    @Override
    public Object post(BaseModel model) {
        var requestBody = model == null ? "" : model;
        return given()
                .spec(requestSpecification)
                .body(requestBody)
                .post(endpoint.getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public Object put(BaseModel model) {
        return null;
    }

    @Override
    public Object delete(int id) {
        return null;
    }
}
