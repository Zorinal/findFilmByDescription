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

@Service
@RequiredArgsConstructor
public class FilmService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    public List<Film> filmList(Principal principal) {
        return getUserByPrincipal(principal).getFilms();
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findUserByUsername(principal.getName()).orElse(new User());
    }

    public void saveFilm(Film film, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.getFilms().add(film);
        film.setUser(user);
        filmRepository.save(film);
    }

}
