package com.bhft.negative;

import com.bhft.BaseTest;
import com.bhft.domain.TodoModel;
import com.bhft.service.HttpService;
import com.bhft.service.HttpServiceImpl;
import com.bhft.service.TodosIdGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.bhft.constant.Constant.STATUS_400;
import static com.bhft.helper.RestHelper.getListTodo;


@Owner("Oleg")
@DisplayName("Negative tests")
public class TodoApiNegativeTest extends BaseTest {

    @ParameterizedTest(name = "We check whether json will be created without the text field")
    @ValueSource(booleans = { true, false })
    void checkingCreationObjectWithoutTextField(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(TodosIdGenerator.INSTANCE.generate(), completed);

        log.info("Step 1: We are trying to put the Todo object without the 'text' field in the list");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_400);

        log.info("Step 2: The object should not be in the list");
        soft.assertThat(getListTodo()).as("check not contain object").doesNotContain(todoModel);
    }

    @Test
    @DisplayName("Checking whether json is being created without the completed field")
    void checkingCreationObjectWithoutCompletedField() {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(TodosIdGenerator.INSTANCE.generate(), "Test_1");

        log.info("Step 1: We are trying to put the Todo object without the 'completed' field in the list");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_400);

        log.info("Step 2: The object should not be in the list");
        soft.assertThat(getListTodo()).as("check not contain object").doesNotContain(todoModel);
    }
}
