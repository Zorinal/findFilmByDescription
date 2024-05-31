package org.vced.filmByDescription.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vced.filmByDescription.models.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

}
