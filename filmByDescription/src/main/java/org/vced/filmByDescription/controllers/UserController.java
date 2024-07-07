package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.services.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    // ссылка на кинопоиск бота
    private static final String KINOPOSIK_BOT = "https://api.telegram.org/bot@kinopoiskdev_bot/";
    // POST запрос уже обрабатывает SpringSecurity
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
    // попытка создать пользователя
    @PostMapping("/registration")
    public String createUser(Model model, User user) {
        if (!userService.createUser(user)) {
            log.error("User {} already exist", user.getUsername());
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/user/{user}")
    public String getUser(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("films", user.getFilms());
        return "user-info";
    }
}
