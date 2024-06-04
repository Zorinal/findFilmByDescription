package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.vced.filmByDescription.services.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ADMIN')") // эти запросы будут доступны только админам
public class AdminController {
    private final UserService userService;
    @GetMapping("/admin")
    public String getAdminPanel(Model model) {
        model.addAttribute("users", userService.users());
        return "users-info";
    }
    @PostMapping("/admin/user/ban/{id}")
    public String banUser(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }
}

