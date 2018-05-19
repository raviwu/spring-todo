package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.RegistrationDTO;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean saveUser(RegistrationDTO registrationDTO);

    boolean modifyUserById(User user);

    boolean deleteUserById(Long id);

}
