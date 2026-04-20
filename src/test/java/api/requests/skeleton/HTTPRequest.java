package api.requests.skeleton;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {

    protected RequestSpecification requestSpecification;
    protected Endpoint endpoint;
    protected ResponseSpecification responseSpecification;
    protected Map<String, Number> pathParams = new HashMap<>();

    public HTTPRequest(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification
            responseSpecification) {
        this.requestSpecification = requestSpecification;
        this.endpoint = endpoint;
        this.responseSpecification = responseSpecification;
    }

}
