package org.vced.filmByDescription.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @GetMapping("/")
    public String films(Model model, Principal principal) {
        model.addAttribute("films", filmService.filmList(principal));
        return "films";
    }
    @PostMapping("/film/create")
    public String filmCreate(Film film, Principal principal) throws JsonProcessingException {

        String url = "http://localhost:5000/api/get";
        Map<String, Object> data = new HashMap<>();
        data.put("description", film.getDescription());
        ResponseEntity<String> response = restTemplate.postForEntity(url, data, String.class);
        System.out.println(response.getBody());
        String name = String.join(" ", objectMapper.readValue(response.getBody(), new TypeReference<List<String>>() {}));
        film.setName(name);
        filmService.saveFilm(film, principal);
        return "redirect:/";
    }
}
