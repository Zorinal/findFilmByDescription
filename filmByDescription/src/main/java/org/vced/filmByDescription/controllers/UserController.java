package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.services.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
    @PostMapping("/registration")
    public String createUser(Model model, User user) {
        log.info("UserController.createUser()");
        if (!userService.createUser(user)) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
        }
        return "redirect:/login";
    }
}
