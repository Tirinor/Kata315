package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AppService;

import javax.validation.Valid;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppService appService;

    @Autowired
    public AdminController(AppService userService) {
        this.appService = userService;
    }

    @GetMapping
    public String findAll(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("allUsers", appService.getAllUsers());
        model.addAttribute("roleUser", appService.listRoles());
        return "admin";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        model.addAttribute("user", appService.getUserById(id));
        model.addAttribute("allRoles", appService.listRoles());
        return "user";
    }

    @PostMapping
    public String createUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                             @ModelAttribute("role") Long roleId) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        return getString(user, roleId);
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        User user = appService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("roleUser", appService.listRoles());
        return "redirect:/admin";
    }

    @PutMapping("/update/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @ModelAttribute("role") Long roleId) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        return getString(user, roleId);
    }

    private String getString(@ModelAttribute("newUser") User user,
                             @ModelAttribute("role") Long roleId) {
        user.setRoles(Set.of(appService.getOneRole(roleId)));
        appService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        appService.removeUser(id);
        return "redirect:/admin";
    }
}