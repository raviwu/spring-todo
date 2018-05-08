package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean saveUser(User user);

    boolean modifyUserById(User user);

}
