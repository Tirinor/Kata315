package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class Init {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private final Role roleAdmin = new Role("ROLE_ADMIN");
    private final Role roleUser = new Role("ROLE_USER");

    public Set<Role> setAdminRole() {
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(roleAdmin);
        adminRole.add(roleUser);
        return adminRole;
    }

    public Set<Role> setRoleUser() {
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleUser);
        return userRole;
    }

    @PostConstruct
    public void addInitUsers() {
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        User admin = new User("admin", "test22", "admin@mail.ru", passwordEncoder.encode("admin"), (byte)10, setAdminRole());
        User user = new User("user", "test11", "user@mail.ru", passwordEncoder.encode("user"), (byte)25, setRoleUser());

        userRepository.save(admin);
        userRepository.save(user);
    }
}
