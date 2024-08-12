package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.ClassifierDbStorage;
import ru.yandex.practicum.filmorate.dal.LikesDbStorage;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.dtoMappers.FilmMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    @Qualifier(value = "dbFilmStorage")
    private final FilmStorage filmStorage;
    @Qualifier(value = "dbUserStorage")
    private final UserStorage userStorage;
    private final LikesDbStorage likesDbStorage;
    private final ClassifierDbStorage classifierDbStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage, LikesDbStorage likesDbStorage, ClassifierDbStorage classifierDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesDbStorage = likesDbStorage;
        this.classifierDbStorage = classifierDbStorage;

    }


    public Film addFilm(FilmRequestDto newFilm) {
        log.info("Загружаем и валидируем фильм из json");
        genreExistsValidate(newFilm.getGenres());
        mpaExistsValidate(newFilm.getMpa());
        FilmValidator.validateFilm(newFilm);
        getMpaById(newFilm.getMpa().getId());
        if (newFilm.getGenres() != null) {
            newFilm.getGenres().stream()
                    .forEach(g -> getGenreById(g.getId()));
        }
        return filmStorage.add((FilmMapper.dtoToFilm(newFilm)));

    }

    public Film updateFilm(FilmRequestDto editedFilm) {
        if (editedFilm.getId() == 0) {
            throw new NotFoundException("Не указан идентификатор пользователя");
        }
        FilmValidator.validateFilm(editedFilm);
        getFilmById(editedFilm.getId());
        return filmStorage.update(FilmMapper.dtoToFilm(editedFilm));
    }

    public void addLike(Long filmId, Long userId) {
        filmNotNullValidate(filmId);
        userNonNullValidate(userId);
        likesDbStorage.addLike(filmId, userId);
        log.trace(String.format("Лайк от пользователя {} добавлен фильму {}", userId, filmId));
    }

    public void deleteLike(Long filmId, Long userId) {
        filmNotNullValidate(filmId);
        userNonNullValidate(userId);
        likesDbStorage.deleteLike(filmId, userId);
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

    public void deleteFilm(long filmId) {
        filmStorage.delete(filmId);
    }

    public Film getFilmById(long filmId) {
        try {
            filmNotNullValidate(filmId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильма с таким id не найдено");
        }
        Film film = filmStorage.getFilm(filmId);
        Mpa mpa = getMpaById(film.getMpa().getId());
        film.setMpa(mpa);
        return film;
    }

    public List<Genre> getAllGenres() {
        return classifierDbStorage.getAllGenres();
    }

    public Genre getGenreById(int genreId) {
        try {
            return classifierDbStorage.getGenreById(genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанра с таким id не найдено");
        }

    }

    public List<Mpa> getAllMpa() {
        return classifierDbStorage.getAllMpa();
    }

    public Mpa getMpaById(int mpaId) {
        try {
            return classifierDbStorage.getMpaById(mpaId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("mpa с таким id не найдено");
        }
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

    private void genreExistsValidate(List<GenreDto> genres) {

        if (genres != null) {
            Optional<GenreDto> failedGenre = null;
            try {
                failedGenre = genres.stream()
                        .filter(genre -> getGenreById(genre.getId()) == null)
                        .findFirst();
            } catch (NotFoundException e) {
                throw new ValidationException("Некорректно значение id жанра");
            }
            if (failedGenre.isPresent()) {
                throw new ValidationException("Жанра не существует");
            }
        }
    }

    private void mpaExistsValidate(MpaDto mpa) {
        try {
            getMpaById(mpa.getId());
        } catch (NotFoundException e) {
            throw new ValidationException("mpa с таким id нет");
        }
    }


}