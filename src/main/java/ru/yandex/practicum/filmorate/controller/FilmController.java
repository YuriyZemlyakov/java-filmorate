package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Long, Film> films = new HashMap<>();
    private final Logger log = (Logger) LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("Загружаем и валидируем фильм из json");
        validateFilm(newFilm);
        newFilm.setId(getNextId());
        log.info("Записи присвоен id {}", newFilm.getId());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PutMapping
    public Film update(@RequestBody Film editedFilm) {
        log.info("Загружаем и валидируем внесенные изменения");
        if (editedFilm.getId() == null) {
            throw new ValidationException("Для обновления данных фильма id должен быть указан");
        }
        if (!films.containsKey(editedFilm.getId())) {
            log.error("Указан несуществующий id");
            throw new NotFoundException("Фильм с таким id не найден");
        }
        validateFilm(editedFilm);
        log.info("Сохраняем обновленные данные");
        Film oldFilm = films.get(editedFilm.getId());
        if (editedFilm.getName() != null) {
            oldFilm.setName(editedFilm.getName());
        }
        if (editedFilm.getDescription() != null) {
            oldFilm.setDescription(editedFilm.getDescription());
        }
        if (editedFilm.getReleaseDate() != null) {
            oldFilm.setReleaseDate(editedFilm.getReleaseDate());
        }
        if (editedFilm.getDuration() != 0) {
            oldFilm.setDuration(editedFilm.getDuration());
        }
        return oldFilm;
    }

    public boolean validateFilm(Film film) {
        boolean isValidationSuccess = false;
        if (film.getName().isBlank() || film.getName() == null) {
            String errorMessage = "Название фильма не может быть пустым";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage, e);
            throw e;
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            ValidationException e = new ValidationException("Максимальная длина описания - 200 символов");
            String errorMessage = "Максимальная длина описания - 200 символов";
            log.error(errorMessage, e);
            throw e;
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String errorMessage = "Дата релиза не может быть раньше 28 декабря 1895 года";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage, e);
            throw e;

        }
        if (film.getDuration() != 0 && film.getDuration() <= 0) {
            String errorMessage = "Продолжительность фильма должна быть положительным числом";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage, e);
            throw e;
        }
        isValidationSuccess = true;
        return isValidationSuccess;
    }


    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
