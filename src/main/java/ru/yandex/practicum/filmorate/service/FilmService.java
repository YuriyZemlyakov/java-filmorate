package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    @Qualifier(value = "dbFilmStorage")
    private final FilmStorage filmStorage;
    @Qualifier(value = "dbUserStorage")
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }


    public Film addFilm(Film newFilm) {
        log.info("Загружаем и валидируем фильм из json");
        FilmValidator.validateFilm(newFilm);
        newFilm.setId(filmStorage.getNextId());
        log.info("Записи присвоен id {}", newFilm.getId());
        filmStorage.add((newFilm));
        return newFilm;
    }

    public Film updateFilm(Film editedFilm) {
        FilmValidator.validateFilm(editedFilm);
        return filmStorage.update(editedFilm);
    }

    public void addLike(Long filmId, Long userId) {
        filmNotNullValidate(filmId);
        userNonNullValidate(userId);
        filmStorage.getFilm(filmId).getLikes().add(userId);
        log.trace(String.format("Лайк от пользователя {} добавлен фильму {}", userId, filmId));
    }

    public void deleteLike(Long filmId, Long userId) {
        filmNotNullValidate(filmId);
        userNonNullValidate(userId);
        filmStorage.getFilm(filmId).getLikes().remove(filmId);
        log.trace("Лайк пользователя {} фильму {} удален", userId, filmId);
    }

    public Collection<Film> getMostLikedFilmes(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted(new FilmComparator().reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    private void filmNotNullValidate(long filmId) {
        if (filmStorage.getFilm(filmId) == null) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
    }

    private void userNonNullValidate(long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с таким id  не найден");
        }
    }


}