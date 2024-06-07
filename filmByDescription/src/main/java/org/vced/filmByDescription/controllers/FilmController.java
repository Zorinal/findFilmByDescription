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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.vced.filmByDescription.models.Film;
import org.vced.filmByDescription.services.FilmService;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    // основная логика
    private final FilmService filmService;
    // нужен для отправки json на python
    @Autowired
    private RestTemplate restTemplate;
    // нужен для того, чтобы корректно читать json (Например, исправлять кодировку)
    @Autowired
    private ObjectMapper objectMapper;
    // доступ к python серверу для получения имени
    private static final String PYTHON_GET_NAME = "http://localhost:5000/name/get";
    // доступ к python серверу для получения информации об имени
    private static final String PYTHON_GET_INFO = "http://localhost:5000/info/get";

    // начало сервиса + взаимодействие с python
    @GetMapping("/")
    public String films(Model model,
                        Principal principal,
                        @RequestParam(name = "description", required = false) String description) {
        // возвращает на html список фильмов текущего пользователя
        model.addAttribute("films", filmService.films(principal));
        model.addAttribute("isAdmin", filmService.getUserByPrincipal(principal).isAdmin());
        if (description != null) {
            try {
                // здесь возникает ошибка, если json неправильный
                List<String> names = sendDescriptionToPython(description);
                model.addAttribute("suggestedFilms", names);
            } catch (JsonProcessingException e) {
                // ошибка выводится в консоль + на фронт
                log.error("Error processing JSON response", e);
                model.addAttribute("errorMessage", "Ошибка обработки ответа от сервера");
            }
        }
        return "films";
    }

    // сохранение фильма
    @PostMapping("/film/create")
    public String filmCreate(Film film, Principal principal) {
        // сохранение фильма в системе
        filmService.saveFilm(film, principal, false);
        return "redirect:/";
    }

    // информация о фильме
    @GetMapping("/film/{name}")
    public String filmInfo(Model model, @PathVariable String name) {
        // отправляем запрос с инфой о фильме
        try {
            List<String> filmInfo = sendNameToPython(name);
            model.addAttribute("info", filmInfo);
            model.addAttribute("name", filmInfo.getFirst());
            model.addAttribute("description", filmInfo.get(1));
        } catch (JsonProcessingException e) {
            // ошибка выводится в консоль + на фронт
            log.error("Error processing JSON response", e);
            model.addAttribute("errorMessage", "Ошибка обработки ответа от сервера");
        }
        return "film-info";
    }
    // пользователь предлагает внести новый фильм
    @PostMapping("/film/suggest")
    public String filmSuggest(Film film, Principal principal){
        // сохранение фильма в системе
        filmService.saveFilm(film, principal, true);
        return "redirect:/";
    }
    // получаем информацию о фильме из python
    private List<String> sendNameToPython(String name) throws JsonProcessingException {
        // формируем json
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        // отправка json на питон
        ResponseEntity<String> response = restTemplate.postForEntity(PYTHON_GET_INFO, data, String.class);
        // парсинг json + возвращает фильмы
        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
    }

    // получаем имя фильма по описанию из python
    private List<String> sendDescriptionToPython(String description) throws JsonProcessingException {
        // формируем json
        Map<String, Object> data = new HashMap<>();
        data.put("description", description);
        // отправка json на питон
        ResponseEntity<String> response = restTemplate.postForEntity(PYTHON_GET_NAME, data, String.class);
        // парсинг json + возвращает фильмы
        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
    }
}
