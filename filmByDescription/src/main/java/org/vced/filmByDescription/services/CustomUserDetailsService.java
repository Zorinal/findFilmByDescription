package org.vced.filmByDescription.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vced.filmByDescription.configurations.CustomUserDetails;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.repositories.UserRepository;

import java.util.Optional;

// Прослойка между CustomUserDetails и SpringSecurity
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // Нужно для того, чтобы брать пользователей из бд
    @Autowired
    private UserRepository userRepository;
    // метод для загрузки пользователя из бд по имени пользователя. Нужен для Spring Security
    // По этому методу Security шифрует пароль для User + сверяет уровень доступа по username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Не пишу логи, иначе консоль засоряется
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("username: " + username + "not found"));
    }
}
