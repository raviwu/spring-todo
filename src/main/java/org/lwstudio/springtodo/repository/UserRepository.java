package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> selectAllUsers();

}
