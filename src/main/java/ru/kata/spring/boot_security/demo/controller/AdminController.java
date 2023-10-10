package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/show/{id}")
    public String showUser(ModelMap model, @PathVariable(value = "id", required = false) Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "show";
    }

    @GetMapping("save")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "saveUser";
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUser(@PathVariable(value = "id", required = false) Long id, ModelMap model) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("rolesUpdate", roleService.getAllRoles());
        return "update";
    }

    @PatchMapping(value = "update")
    public String update(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable(value = "id", required = false) Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}