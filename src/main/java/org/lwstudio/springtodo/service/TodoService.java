package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.model.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    List<Todo> getTodosByUserId(Long userId);

    Optional<Todo> getTodoById(Long id);

    boolean modifyTodoDescriptionById(Todo todo);
}
