package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vced.filmByDescription.models.Film;
import org.vced.filmByDescription.services.FilmService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class FilmController {
    // example
    private final FilmService filmService;
    @GetMapping("/")
    public String films(Model model, Principal principal) {
        model.addAttribute("films", filmService.filmList(principal));
        return "films";
    }
    @PostMapping("/film/create")
    public String filmCreate(Film film, Principal principal) {
        filmService.saveFilm(film, principal);
        return "redirect:/";
    }
}
