package org.vced.filmByDescription.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vced.filmByDescription.models.Role;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        log.info("UserService.createUser()");
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            log.info("User {} already exists", user.getUsername());
            return false;
        }
        user.setActive(true);
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} has been created", user.getUsername());

        return true;
    }
}
