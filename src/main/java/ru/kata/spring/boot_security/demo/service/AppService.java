package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface AppService {
    User getUserById(Long id);
    List<Role> listRoles();
    List<User> getAllUsers();
    void saveUser(User user);
    void removeUser(Long id);
    Role getOneRole(Long id);
    User findOne(Long id);
}
