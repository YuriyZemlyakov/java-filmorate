package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film);

    void delete(Long filmId);

    Film update(Film film);

    Long getNextId();

    Collection<Film> getAllFilms();

    Film getFilm(Long filmId);

}
