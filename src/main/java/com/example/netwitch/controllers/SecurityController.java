package com.example.netwitch.controllers;

import com.example.netwitch.models.User;
import com.example.netwitch.models.enums.Role;
import com.example.netwitch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;
    private boolean start = false;

    @GetMapping("/login")
    public String login() {
        if (!start) {
            userService.createUser(new User());
            start = true;
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration1() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/userList")
    public String showList(Model model) {
        model.addAttribute("users", userService.showUsers());
        return "userList";
    }

    @GetMapping("/admin/edit/{user}")
    public String edit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/admin/edit")
    public String save(
            @RequestParam String name,
            @RequestParam Map<String, String> form,
            @RequestParam("id") User user
    ) {
        userService.saveAdminEdit(name, form, user);
        return "redirect:/admin/userList";
    }
}
