package com.bhft.service;

import com.bhft.domain.TodoModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public interface HttpService {
    ValidatableResponse sendGet(int expectedStatusCode);

    void sendPost(TodoModel todoModel, int expectedStatusCode);

    void sendPut(TodoModel todoModel,  int expectedStatusCode);

    void sendDelete(TodoModel todoModel, int expectedStatusCode);

    List<TodoModel> sendGetOffsetAndLimit(int offset, int limit, int expectedStatusCode);
}
