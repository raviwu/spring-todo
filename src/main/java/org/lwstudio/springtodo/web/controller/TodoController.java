package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.constant.ResourceName;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.service.UserService;

import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.service.TodoService;

import org.lwstudio.springtodo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/users/{userId}/todos")
@PreAuthorize("principal.id == #userId")
public class TodoController {

    private TodoService todoService;
    private UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getTodosByUserId(@PathVariable Long userId) {
        assertUserExist(userId);

        return ResponseEntity.ok(todoService.getTodosByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Long userId, @PathVariable Long id) {
        assertUserExist(userId);
        assertTodoExist(id);

        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<?> postTodo(@PathVariable Long userId, @RequestBody Todo todo) {
        assertUserExist(userId);

        todo.setUserId(userId);
        todoService.saveTodo(todo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todo.getId())
                .toUri();

        return ResponseEntity.created(location).body(todoService.getTodoById(todo.getId()));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodoDescriptionById(@PathVariable Long userId, @PathVariable Long id, @RequestBody Todo todo) {
        assertUserExist(userId);
        assertTodoExist(id);

        todo.setId(id);

        todoService.modifyTodoDescriptionById(todo);

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoById(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> updateTodoDescriptionById(@PathVariable Long userId, @PathVariable Long id) {
        assertUserExist(userId);
        assertTodoExist(id);

        todoService.completeTodoById(id);

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long userId, @PathVariable Long id) {
        assertUserExist(userId);
        assertTodoExist(id);

        todoService.deleteTodoById(id);

        return ResponseEntity.noContent().build();
    }

    // Helper Method
    private void assertUserExist(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            throw new ResourceNotFoundException(ResourceName.USER, "Id", userId);
        }
    }

    private void assertTodoExist(Long id) {
        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new ResourceNotFoundException(ResourceName.TODO, "Id", id);
        }
    }
}
