package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {
    public static boolean validateFilm(FilmRequestDto film) {
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
}
