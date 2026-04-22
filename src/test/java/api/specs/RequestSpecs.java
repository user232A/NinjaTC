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

    private static Map<String, String> authHeaders = new HashMap<>(Map.of("admin", "Bearer eyJ0eXAiOiAiVENWMiJ9.czUyZEo2WC1yazJoYmUyUkpmN21jVGhEY2Nv.NjQ1NjBjMDktMzkwNy00YWU1LWI5Y2MtMmZiZDk2ZmMzYTIy"));

    private RequestSpecs() {

    }

    private static RequestSpecBuilder defaultRequestBuilder() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .setBaseUri(Config.getProperty("apiBaseUrl"));
    }

    public static RequestSpecification unAuthSpec() {
        return defaultRequestBuilder().build();
    }

    public static RequestSpecification adminSpec() {
        return defaultRequestBuilder()
                .addHeader("Authorization", authHeaders.get("admin"))
                .build();
    }

    public static RequestSpecification userSpec(String token) {
        return defaultRequestBuilder()
                .addHeader("Authorization", token)
                .build();
    }
}
