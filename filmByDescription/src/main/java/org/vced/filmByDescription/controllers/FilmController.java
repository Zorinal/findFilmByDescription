package org.vced.filmByDescription.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.vced.filmByDescription.models.Film;
import org.vced.filmByDescription.services.FilmService;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/")
    public String films(Model model, Principal principal) {
        model.addAttribute("films", filmService.filmList(principal));
        return "films";
    }
    @PostMapping("/film/create")
    public String filmCreate(Film film, Principal principal) {
        String url = "http://localhost:5000/api/get";
        Map<String, Object> data = new HashMap<>();
        data.put("filmName", film.getName());
        ResponseEntity<String> response = restTemplate.postForEntity(url, data, String.class);

        String editedFilmName = response.getBody();

        film.setName(editedFilmName);

        filmService.saveFilm(film, principal);
        return "redirect:/";
    }
}
