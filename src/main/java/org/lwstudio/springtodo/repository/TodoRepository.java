package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository extends CrudRepository<Todo, Long> {

}
