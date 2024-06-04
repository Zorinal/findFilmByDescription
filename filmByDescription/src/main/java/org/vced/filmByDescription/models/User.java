package org.vced.filmByDescription.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private boolean active;

    // Создаёт вспомогательную таблицу для ролей
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // Задаём параметры вспомогательной таблицы (имя + по какому полю объединять две таблицы)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // преобразует элементы коллекций в тип String
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    // У одного юзера много фильмов (история поиска),
    // при удалении юзера, удаляются все связанные с ним фильмы
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Film> films = new ArrayList<>();

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }
}
