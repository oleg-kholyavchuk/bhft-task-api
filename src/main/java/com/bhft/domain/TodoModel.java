package com.bhft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoModel {
    private Long id;
    private String text;
    private Boolean completed;

    public TodoModel(Long id, Boolean completed) {
        this.id = id;
        this.completed = completed;
    }

    public TodoModel(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
