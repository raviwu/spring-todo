package org.lwstudio.springtodo.security;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.selectUserByUsername(username);

        if (user == null) {
            return JwtUserFactory.create(new User());
        } else {
            return JwtUserFactory.create(user);
        }
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.selectUserById(id);

        if (user == null) {
            return JwtUserFactory.create(new User());
        } else {
            return JwtUserFactory.create(user);
        }
    }

}