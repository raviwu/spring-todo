package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    List<Todo> selectTodosByUserId(Long userId);

    Todo selectTodoById(Long id);

    Integer deleteTodosByUserId(Long userId);

}
