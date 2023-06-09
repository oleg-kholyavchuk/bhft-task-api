package com.bhft.service;

import com.bhft.domain.TodoModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public interface HttpService {
    ValidatableResponse sendGet(int statusCode);

    void sendPost(TodoModel todoModel, int statusCode);

    void sendPut(TodoModel todoModel,  int statusCode);

    void sendDelete(TodoModel todoModel, int statusCode);

    List<TodoModel> sendGetOffsetAndLimit(int offset, int limit);
}
