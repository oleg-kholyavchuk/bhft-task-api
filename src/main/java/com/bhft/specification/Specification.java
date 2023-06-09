package com.bhft.specification;

import com.bhft.domain.TodoModel;
import com.bhft.util.Props;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.bhft.helper.Supporting.jsonToString;

public class Specification {
    public static final String URL = Props.getProperty("url");

    public static RequestSpecification defaultRequestSpecification(TodoModel todoModel) {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .setBody(jsonToString(todoModel))
                .build();
    }
}
