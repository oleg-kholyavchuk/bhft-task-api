package com.bhft.specification;

import com.bhft.domain.TodoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import static com.bhft.constant.Constant.URL;

@SuppressWarnings("All")
public class Specification {
    @SneakyThrows
    public static String jsonToString(Object o) {
        return new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(o);
    }

    public static RequestSpecification defaultRequestSpecification(TodoModel todoModel) {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .setBody(jsonToString(todoModel))
                .build();
    }
}
