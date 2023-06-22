package com.bhft.service;

import com.bhft.domain.TodoModel;
import com.bhft.util.Props;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.bhft.constant.Constant.*;
import static io.restassured.RestAssured.given;


public enum TodosIdGenerator {
    INSTANCE;
    private final List<Long> listIds = new CopyOnWriteArrayList<>();
    private static final String DEFAULT_LOGIN = Props.getProperty("login");
    private static final String DEFAULT_PASSWORD = Props.getProperty("password");

    public synchronized long generate() {
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

        long randomValue = (long) ((Math.random() * 99) + 1);

        if (listId.contains(randomValue) || listIds.contains(randomValue)) {
            return generate();
        }
        listIds.add(randomValue);
        return randomValue;
    }

    public synchronized void remove(long id) {
        listIds.remove(id);

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
