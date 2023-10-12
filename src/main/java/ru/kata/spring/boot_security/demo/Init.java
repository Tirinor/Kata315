package ru.kata.spring.boot_security.demo;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class Init {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public Init(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        var userRoles = new ArrayList<Role>();
        var adminRoles = new ArrayList<Role>();

        userRoles.add(roleUser);
        adminRoles.add(roleAdmin);

        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        // Пароль - имя юзера
        User admin = new User("admin", "test2", "admin@mail.ru",
                "$2a$12$R7UwBqhVMUHlvoyQrwnT9upAry2qvrDLdRkN6YFd0TEdyOWqCUdya");
        User user = new User("user", "test1", "user@mail.ru",
                "$2a$12$jl2mZEZR2p3uVnyWGgz/s.BGm7nhqzPC.Y5CqZsEoNpqLHBFkhs9O");

        user.setRoles(userRoles);
        admin.setRoles(adminRoles);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
