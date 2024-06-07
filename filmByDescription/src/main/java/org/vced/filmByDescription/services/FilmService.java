package org.vced.filmByDescription.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vced.filmByDescription.models.Film;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.repositories.FilmRepository;
import org.vced.filmByDescription.repositories.UserRepository;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

// основная логика взаимодействия с фильмами
@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private static final String DATA_PATH = "data.csv";
    // возвращает все фильмы пользователя
    public List<Film> films(Principal principal) {
        log.info("FilmService.filmList()");
        return getUserByPrincipal(principal).getFilms();
    }
    // возвращает юзера по текущей сессии
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findUserByUsername(principal.getName()).orElse(new User());
    }
    public void saveFilm(Film film, Principal principal, boolean isModerated) {
        log.info("FilmService.saveFilm()");
        User user = getUserByPrincipal(principal);
        // нужно связать пользователя с фильмом и фильм с пользователем
        user.getFilms().add(film);
        film.setUser(user);
        // либо пользователь нашёл фильм через поиск, либо он предложил данный фильм
        film.setModerated(isModerated);
        filmRepository.save(film);
        log.info("Film {} was saved to DB", film.getName());

    }
    // возвращает фильмы, не прошедшие модерацию
    public List<Film> moderatedFilms() {
        log.info("FilmService.moderatedFilms()");
        return filmRepository.findAll().stream().filter(Film::isModerated).collect(Collectors.toList());
    }
    // если фильм одобрен, то сохраняем в таблицу, иначе удаляем
    public void approveFilm(Long id, boolean isApproved) {
        log.info("UserService.approveFilm()");
        Film film = filmRepository.findById(id).orElse(new Film());
        if (isApproved) {
            film.setModerated(false);
            addToTable(film);
            filmRepository.save(film);
            log.info("Film {} was approved", film.getName());
        }else {
            filmRepository.delete(film);
            log.info("Film {} was not approved", film.getName());
        }
    }
    // сохраняем одобренный фильм в excel таблицу
    public void addToTable(Film film){
        try(FileWriter fw = new FileWriter(DATA_PATH, true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.write(film.getDescription() + ",");
            pw.write(film.getName() + "\n");
        } catch (IOException e) {
            log.error("File {} not found", DATA_PATH);
            throw new RuntimeException();
        }
    }
}
