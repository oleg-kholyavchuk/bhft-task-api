package com.bhft.service;

import com.bhft.domain.TodoModel;
import com.bhft.util.Props;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.bhft.constant.Constant.STATUS_204;
import static com.bhft.constant.Constant.TODOS;
import static io.restassured.RestAssured.given;


public enum WorkWithTodoId {
    INSTANCE;
    private final List<Long> testId = new CopyOnWriteArrayList<>();
    private static final String URL = Props.getProperty("url");
    private static final String DEFAULT_LOGIN = Props.getProperty("login");
    private static final String DEFAULT_PASSWORD = Props.getProperty("password");

    public synchronized long generateId() {
        Response response = RestAssured
                .given()
                .baseUri(URL)
                .when()
                .get(TODOS)
                .then()
                .extract()
                .response();

        List<Long> listId = response.body().jsonPath().getList(".", TodoModel.class)
                .stream().map(TodoModel::getId).collect(Collectors.toList());

        long randomValue = (long) (Math.random() * (100 + 1));

        if (listId.contains(randomValue) || testId.contains(randomValue)) {
            return generateId();
        }
        testId.add(randomValue);
        return randomValue;
    }

    public synchronized void removeId(long id) {
        testId.remove(id);

        Response response = RestAssured
                .given()
                .baseUri(URL)
                .when()
                .get(TODOS)
                .then()
                .extract()
                .response();

        if (response.body().jsonPath().getList(".", TodoModel.class)
                .stream().map(TodoModel::getId).anyMatch(toDoId -> toDoId == id)) {
            given()
                    .baseUri(URL)
                    .auth()
                    .preemptive()
                    .basic(DEFAULT_LOGIN, DEFAULT_PASSWORD)
                    .when()
                    .delete(TODOS + "/" + id)
                    .then()
                    .statusCode(STATUS_204)
                    .log().all();
        }
    }
}
