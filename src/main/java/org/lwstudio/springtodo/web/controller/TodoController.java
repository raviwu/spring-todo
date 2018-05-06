package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TodoController {
    @Autowired
    private TodoRepository repository;
}
