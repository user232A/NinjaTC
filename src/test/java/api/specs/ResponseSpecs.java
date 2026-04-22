package api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class ResponseSpecs {

    private ResponseSpecs() {

    }

    private static ResponseSpecBuilder defaultResponseBuilder() {
        return new ResponseSpecBuilder();
    }

    public static ResponseSpecification entityWasCreated() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED).build();
    }

    public static ResponseSpecification operationOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification unAuthorizedUser() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .build();
    }

    public static ResponseSpecification notFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }
}
