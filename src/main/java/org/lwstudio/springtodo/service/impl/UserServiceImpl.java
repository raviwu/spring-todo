package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.RegistrationDTO;
import org.lwstudio.springtodo.repository.UserRepository;
import org.lwstudio.springtodo.repository.TodoRepository;
import org.lwstudio.springtodo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.selectAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.selectUserById(id);
    }

    @Override
    @Transactional
    public boolean saveUser(RegistrationDTO registrationDTO) {
        return userRepository.insertUser(registrationDTO) > 0;
    }

    @Override
    public boolean modifyUserById(User user) {
        return userRepository.updateUserById(user) > 0;
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        todoRepository.deleteTodosByUserId(id);

        return userRepository.deleteUserById(id) > 0;
    }

}
