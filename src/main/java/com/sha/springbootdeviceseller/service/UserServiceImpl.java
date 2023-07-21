package com.sha.springbootdeviceseller.service;

import com.sha.springbootdeviceseller.model.Role;
import com.sha.springbootdeviceseller.model.User;
import com.sha.springbootdeviceseller.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional //Transactional is required when executing on update/delete query
    public void changeRole(Role newRole, String username){
        userRepository.updateUserRole(username, newRole);
    }
}
