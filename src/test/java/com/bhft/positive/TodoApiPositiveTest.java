package com.bhft.positive;

import com.bhft.BaseTest;
import com.bhft.domain.TodoModel;
import com.bhft.service.HttpService;
import com.bhft.service.HttpServiceImpl;
import com.bhft.service.WorkWithTodoId;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.bhft.constant.Constant.*;
import static com.bhft.helper.Supporting.*;

@Owner("Oleg")
@DisplayName("Positive tests. We check the operation of the main functionality")
public class TodoApiPositiveTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Creating a json object by the post method, and then trying to return it by the get method")
    public void checkingTheGetMethod(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), "Test", completed);

        log.info("Step 1: Putting the created Todo object in an array");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_201);

        List<TodoModel> todoModelList = httpServiceImpl.sendGet(STATUS_200).extract()
                .body()
                .jsonPath()
                .getList(".", TodoModel.class);

        log.info("Step 2: Deleting the Todo object from the array");
        softAssertions.assertThat(todoModelList.contains(todoModel))
                .as("checking whether the object is contained").isTrue();

        log.info("Post-condition : Deleting the added entities");
        WorkWithTodoId.INSTANCE.removeId(todoModel.getId());
        softAssertions.assertThat(getListTodo()).as("check not contain json object").doesNotContain(todoModel);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Create a new json object and check that the list now contains this object." +
            "Then delete this json object and check that this object does not exist")
    public void checkingThePostMethod(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), "Test", completed);

        log.info("Step 1: Putting the created Todo object in an array");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_201);

        log.info("Step 2: We check that the array contains the object we need");
        softAssertions.assertThat(getListTodo()).as("check json object").contains(todoModel);

        log.info("Post-condition : Deleting the added entities");
        WorkWithTodoId.INSTANCE.removeId(todoModel.getId());
        softAssertions.assertThat(getListTodo()).as("check not contain json object").doesNotContain(todoModel);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Creating a json object Todo. Then we check whether the json object has been removed from the list," +
            " and whether the list is empty")
    public void checkIfTheDeleteMethod(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), "Test", completed);

        log.info("Step 1: Putting the created Todo object in an array");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_201);

        log.info("Step 2: Deleting the Todo object from the array");
        httpServiceImpl.sendDelete(todoModel, STATUS_204);
        softAssertions.assertThat(getListTodo()).as("check empty").doesNotContain(todoModel);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Creating a json object Todo. Then we change the text field. Check if the text field has changed." +
            "Then delete the json object")
    public void checkIfThePutMethod(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        TodoModel todoModel = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), "Test", completed);

        log.info("Step 1: Putting the created Todo object in an array");
        HttpService httpServiceImpl = new HttpServiceImpl();
        httpServiceImpl.sendPost(todoModel, STATUS_201);

        log.info("Step 2: Field changes 'text'");
        todoModel.setText("Test_333");

        log.info("Step 3: Changing the 'text' fields in the object");
        httpServiceImpl.sendPut(todoModel, STATUS_200);

        log.info("Step 4: Check that the 'text' field has changed");
        softAssertions.assertThat(todoModel.getText()).as("check text")
                .isEqualTo("Test_333");

        log.info("Post-condition : Deleting the added entities");
        WorkWithTodoId.INSTANCE.removeId(todoModel.getId());
        softAssertions.assertThat(getListTodo()).as("check empty").isNotIn(todoModel);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Creating json Todo objects. Then we add it to the list. " +
            "We specify the limit and the position of the objects,which we want to check for presence in the list")
    public void checkTaskByOffset(Boolean completed) {
        log.info("Precondition: Creating a Todo object");
        String text = "Test_3";
        TodoModel todoModel1 = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), text, completed);
        TodoModel todoModel2 = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), text, completed);
        TodoModel todoModel3 = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), text, completed);
        TodoModel todoModel4 = new TodoModel(WorkWithTodoId.INSTANCE.generateId(), text, completed);

        log.info("Step 1: Creating a Todo list");
        List<TodoModel> list = List.of(todoModel1, todoModel2, todoModel3, todoModel4);
        addListObjectTodo(list);
        List<TodoModel> allList = getListTodo();

        log.info("Step 2: Creating a list of the difference between two lists");
        HttpService httpServiceImpl = new HttpServiceImpl();
        List<TodoModel> listDifference1 = listDifference(allList, httpServiceImpl.sendGetOffsetAndLimit(0, 0));
        List<TodoModel> listDifference2 = listDifference(allList, httpServiceImpl.sendGetOffsetAndLimit(0, 1));
        List<TodoModel> listDifference3 = listDifference(allList, httpServiceImpl.sendGetOffsetAndLimit(1, 0));
        List<TodoModel> listDifference4 = listDifference(allList, httpServiceImpl.sendGetOffsetAndLimit(1, 1));

         softAssertions.assertThat(allList.size()).as("checking the size of two lists ").isEqualTo(listDifference1.size());
         softAssertions.assertThat(allList.size() - 1).as("checking the size of two lists").isEqualTo(listDifference2.size());
         softAssertions.assertThat(allList.size()).as("checking the size of two lists").isEqualTo(listDifference3.size());
         softAssertions.assertThat(allList.size() - 1).as("checking the size of two lists").isEqualTo(listDifference4.size());

        log.info("Post-condition : Deleting the added entities");
        listRemoveId(list);
        softAssertions.assertThat(getListTodo()).as("check empty").isNotIn(list);
    }
}
