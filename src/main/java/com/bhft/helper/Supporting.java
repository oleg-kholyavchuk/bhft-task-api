package com.bhft.helper;

import com.bhft.domain.TodoModel;
import com.bhft.service.WorkWithTodoId;
import com.bhft.util.Props;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

import static com.bhft.constant.Constant.STATUS_201;
import static com.bhft.constant.Constant.TODOS;
import static io.restassured.RestAssured.given;

public class Supporting {
    public static final String URL = Props.getProperty("url");

    public static List<TodoModel> getListTodo() {
        ValidatableResponse validatableResponse = given()
                .baseUri(URL)
                .when()
                .get(TODOS)
                .then()
                .contentType(ContentType.JSON);

        return validatableResponse
                .extract()
                .body()
                .jsonPath()
                .getList(".", TodoModel.class);
    }

    @SneakyThrows
    public static String jsonToString(Object o) {
        return new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(o);
    }

    public static void addListObjectTodo(List<TodoModel> list) {
        for (TodoModel todoModel : list) {
            given()
                    .baseUri(URL)
                    .contentType(ContentType.JSON)
                    .body(jsonToString(todoModel))
                    .when()
                    .post(TODOS)
                    .then()
                    .statusCode(STATUS_201)
                    .log().all();
        }
    }

    public static void listRemoveId(List<TodoModel> list) {
        for (TodoModel todoModel : list) {
            WorkWithTodoId.INSTANCE.removeId(todoModel.getId());
        }
    }

    public static List<TodoModel> listDifference(List<TodoModel> firstList, List<TodoModel> secondList) {
        if(secondList.size() == 0) {
            return firstList;
        }

        return firstList.stream()
                .filter(element -> !secondList.contains(element))
                .collect(Collectors.toList());
    }
}

