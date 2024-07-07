package org.vced.filmByDescription.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vced.filmByDescription.models.Role;
import org.vced.filmByDescription.models.User;
import org.vced.filmByDescription.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {
    private final UserService userService;
    // админ по умолчанию
    @Bean
    CommandLineRunner initAdminUser() {
        return _ -> {
            if (!userService.existsByUsername("admin")) {
                var adminRoles = new HashSet<Role>();
                adminRoles.add(Role.ADMIN);
                var user = User.builder()
                        .name("admin")
                        .username("admin")
                        .password("admin")
                        .roles(adminRoles)
                        .build();
                userService.createUser(user);
            }
        };
    }
}
