package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.vced.filmByDescription.services.FilmService;
import org.vced.filmByDescription.services.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ADMIN')") // эти запросы будут доступны только админам
public class AdminController {
    private final UserService userService;
    private final FilmService filmService;
    private static final String DATA_PATH = "data.csv";

    @GetMapping("/admin")
    public String getAdminPanel(Model model) {
        model.addAttribute("users", userService.users());
        model.addAttribute("films", filmService.moderatedFilms());
        return "admin";
    }
    @PostMapping("/admin/user/ban/{id}")
    public String banUser(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }
    @PostMapping("/admin/film/approve/{id}")
    public String approveFilm(@PathVariable("id") Long id, boolean isApproved) {
        filmService.approveFilm(id, isApproved);
        return "redirect:/admin";
    }
    @GetMapping("/download/data.csv")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        File file = new File(DATA_PATH);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.parseMediaType("application/csv"))
                .contentLength(file.length())
                .body(resource);
    }
}

