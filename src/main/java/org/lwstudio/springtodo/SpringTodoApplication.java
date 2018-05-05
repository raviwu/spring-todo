package org.lwstudio.springtodo;

import org.lwstudio.springtodo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SpringTodoApplication {

	@Autowired
	private TodoRepository repository;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/todos")
	@ResponseBody
	String todos() {
		return this.repository.findAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringTodoApplication.class, args);
	}
}
