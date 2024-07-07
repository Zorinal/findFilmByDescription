package org.vced.filmByDescription.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vced.filmByDescription.models.Role;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.repositories.UserRepository;

import java.util.List;

// основная логика взаимодействия с пользователями
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public boolean createUser(User user) {
        log.info("UserService.createUser()");
        // существует ли данный пользователь
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            log.info("User {} already exists", user.getUsername());
            return false;
        }
        // устанавливаем базовые параметры для юзера
        user.setActive(true);
        user.getRoles().add(Role.USER); // Здесь можно поменять на Role.ADMIN
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} has been created", user.getUsername());
        return true;
    }
    public List<User> users(){
        log.info("UserService.users()");
        // все пользователи из бд
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        log.info("UserService.banUser()");
        User user = userRepository.findById(id).orElse(null);
        // null значит такого пользователя нет
        if (user != null) {
            // бан, если он актив, иначе разбан
            if (user.isActive()){
                user.setActive(false);
                log.info("User with id = {} was banned", id);
            } else {
                user.setActive(true);
                log.info("User with id = {} was unbanned", id);
            }
            userRepository.save(user);
            log.info("User with id = {} was saved to DB", id);
        } else log.info("User with id = {} is not existed", id);
    }
    public boolean existsByUsername(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }
}
