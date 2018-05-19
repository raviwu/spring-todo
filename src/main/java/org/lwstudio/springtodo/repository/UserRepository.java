package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.RegistrationDTO;

import java.util.List;

public interface UserRepository {

    List<User> selectAllUsers();

    User selectUserById(Long id);

    Integer insertUser(RegistrationDTO registrationDTO);

    Integer updateUserById(User user);

    Integer deleteUserById(Long id);

}
