package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {
    public static boolean validateUser(User user) {
        boolean isUserValidationSuccess = false;
        if (user.getEmail() != null && user.getEmail().isBlank()) {
            String errorMessage = "email не может быть пустым";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage);
            throw e;
        }
        if (user.getEmail() != null && !user.getEmail().contains("@")) {
            String errorMessage = "email должен содержать символ @";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage);
            throw e;
        }
        if (user.getLogin() != null && user.getLogin().contains(" ")) {
            String errorMessage = "логин не может содержать пробелы";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage);
            throw e;
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            String errorMessage = "логин не может быть пустым";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage);
            throw e;
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            String errorMessage = "дата рождения не может быть в будущем";
            ValidationException e = new ValidationException(errorMessage);
            log.error(errorMessage);
            throw e;
        }
        isUserValidationSuccess = true;
        return isUserValidationSuccess;
    }

}
