package org.vced.filmByDescription.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vced.filmByDescription.models.Film;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.repositories.FilmRepository;
import org.vced.filmByDescription.repositories.UserRepository;

import java.security.Principal;
import java.util.List;

// основная логика взаимодействия с фильмами
@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    // возвращает все фильмы пользователя
    public List<Film> filmList(Principal principal) {
        return getUserByPrincipal(principal).getFilms();
    }
    // возвращает юзера по текущей сессии
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findUserByUsername(principal.getName()).orElse(new User());
    }
    public void saveFilm(Film film, Principal principal) {
        User user = getUserByPrincipal(principal);
        // нужно связяать пользователя с фильмом и фильм с пользователем
        user.getFilms().add(film);
        film.setUser(user);
        filmRepository.save(film);
    }

}
