package ru.yandex.practicum.filmorate.dal;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Repository("dbFilmStorage")
@Primary
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film add(Film film) {
        return null;
    }

    @Override
    public void delete(Long filmId) {

    }

    @Override
    public Film update(Film editedFilm) {
        return null;
    }

    @Override
    public Long getNextId() {
        return null;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film getFilm(Long filmId) {
        return null;
    }
}
