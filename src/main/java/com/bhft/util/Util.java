package com.bhft.util;

import com.bhft.domain.TodoModel;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static List<TodoModel> listDifference(List<TodoModel> firstList, List<TodoModel> secondList) {
        return firstList.stream()
                .filter(element -> !secondList.contains(element))
                .collect(Collectors.toList());
    }
}
