package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.repository.TodoRepository;
import org.lwstudio.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.selectTodosByUserId(userId);
    }

    @Override
    public Optional<Todo> getTodoById(Long id) {
        return Optional.ofNullable(todoRepository.selectTodoById(id));
    }

    @Override
    public boolean modifyTodoDescriptionById(Todo todo) {
        return todoRepository.updateTodoOnDescriptionById(todo) > 0;
    }

}
