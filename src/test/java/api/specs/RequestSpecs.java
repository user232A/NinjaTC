package api.specs;

import api.configs.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSpecs {

    private static final Map<String, String> authHeaders = new HashMap<>(Map.of("admin", "Basic YWRtaW46YWRtaW4="));

    private RequestSpecs() {

    }

    private static RequestSpecBuilder defaultRequestBuilder(ContentType contentType, ContentType accept) {
        return new RequestSpecBuilder()
                .setContentType(contentType)
                .setAccept(accept)
                .addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .setBaseUri(Config.getProperty("apiBaseUrl"));
    }

    private static RequestSpecBuilder jsonRequestBuilder() {
        return defaultRequestBuilder(ContentType.JSON, ContentType.JSON);
    }

    private static RequestSpecBuilder textRequestBuilder() {
        return defaultRequestBuilder(ContentType.TEXT, ContentType.TEXT);
    }

    public static RequestSpecification unAuthSpec() {
        return jsonRequestBuilder().build();
    }

    public static RequestSpecification adminSpec() {
        return jsonRequestBuilder()
                .addHeader("Authorization", authHeaders.get("admin"))
                .build();
    }

    public static RequestSpecification adminTextSpec() {
        return textRequestBuilder()
                .addHeader("Authorization", authHeaders.get("admin"))
                .build();
    }

    public static RequestSpecification userSpec(String token) {
        return jsonRequestBuilder()
                .addHeader("Authorization", token)
                .build();
    }
}
