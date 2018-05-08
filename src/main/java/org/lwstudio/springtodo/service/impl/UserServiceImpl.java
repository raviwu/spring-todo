package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.repository.UserRepository;
import org.lwstudio.springtodo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.selectAllUsers();
  }

  @Override
  @Transactional
  public boolean saveUser(User user) {
    return userRepository.insertUser(user) > 0;
  }

}
