package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/todos")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> getTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getTodosByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodoDescriptionById(@PathVariable Long id, @RequestBody Todo todo) {
        todo.setId(id);

        todoService.modifyTodoDescriptionById(todo);

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoById(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> updateTodoDescriptionById(@PathVariable Long id) {
        todoService.completeTodoById(id);

        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodoById(id);

        return ResponseEntity.noContent().build();
    }

}
