package com.bhft.service;

import com.bhft.domain.TodoModel;
import com.bhft.util.Props;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static com.bhft.constant.Constant.TODOS;
import static com.bhft.constant.Constant.URL;
import static com.bhft.specification.Specification.defaultRequestSpecification;
import static io.restassured.RestAssured.given;

public class HttpServiceImpl implements HttpService {
    private static final String DEFAULT_LOGIN = Props.getProperty("login");
    private static final String DEFAULT_PASSWORD = Props.getProperty("password");

    @Override
    public ValidatableResponse sendGet(int expectedStatusCode) {
        return given()
                .baseUri(URL)
                .when()
                .get(TODOS)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(expectedStatusCode)
                .log().all();
    }

    public void sendPost(TodoModel todoModel, int exceptedStatusCode) {
        given()
                .spec(defaultRequestSpecification(todoModel))
                .when()
                .post(TODOS)
                .then()
                .statusCode(exceptedStatusCode)
                .log().all();
    }

    public void sendPut(TodoModel todoModel, int expectedStatusCode) {
        given()
                .spec(defaultRequestSpecification(todoModel))
                .when()
                .put(TODOS + "/" + todoModel.getId())
                .then()
                .statusCode(expectedStatusCode)
                .log().all();
    }

    public void sendDelete(TodoModel todoModel, int expectedStatusCode) {
        given()
                .baseUri(URL)
                .auth()
                .preemptive()
                .basic(DEFAULT_LOGIN, DEFAULT_PASSWORD)
                .when()
                .delete(TODOS + "/" + todoModel.getId())
                .then()
                .statusCode(expectedStatusCode)
                .log().all();
    }

    public List<TodoModel> sendGetOffsetAndLimit(int offset, int limit, int expectedStatusCode) {
        ValidatableResponse response = given()
                .baseUri(URL)
                .when()
                .param("offset", offset)
                .param("limit", limit)
                .log().all()
                .get(TODOS)
                .then()
                .statusCode(expectedStatusCode)
                .log().all();

        return response
                .extract()
                .body()
                .jsonPath()
                .getList(".", TodoModel.class);
    }
}
