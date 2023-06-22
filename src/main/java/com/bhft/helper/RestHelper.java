package com.bhft.helper;

import com.bhft.domain.TodoModel;
import com.bhft.service.HttpServiceImpl;
import com.bhft.service.TodosIdGenerator;

import java.util.List;

import static com.bhft.constant.Constant.*;

public class RestHelper {

    public static List<TodoModel> getListTodo() {
        return new HttpServiceImpl().sendGet(STATUS_200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", TodoModel.class);
    }

    public static void addListTodos(List<TodoModel> list) {
        list.forEach(x -> new HttpServiceImpl().sendPost(x, STATUS_201));
    }

    public static void removeListTodos(List<TodoModel> list) {
        list.forEach(x -> TodosIdGenerator.INSTANCE.remove(x.getId()));
    }
}

