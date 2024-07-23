package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest

@DirtiesContext
class FilmorateApplicationTests {

    @Test
    public void validateFilmTest() {
        Film goodFilm = new Film("T", "Drama", LocalDate.of(2000, 1, 1), 200);

        Film filmWithoutName = new Film("", "Drama", LocalDate.of(2000, 1, 1), 200);
        assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(filmWithoutName));

        Film filmWithTooLongDescription = new Film("T", "Drama11111111111111111111111111111111111111111111wwwww" +
                "2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222" +
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "ddddddddddddddddkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", LocalDate.of(2000, 1, 1), 200);
        assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(filmWithTooLongDescription));

        Film filmWithWrongDate = new Film("T", "Drama", LocalDate.of(1725, 1, 1), 200);
        assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(filmWithWrongDate));

        Film filmWithNegativeDuration = new Film("T", "Drama", LocalDate.of(2000, 1, 1), (-2));
        assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(filmWithNegativeDuration));
    }

    @Test
    public void validateUserTest() {
        String errorMessage = null;
        User goodUser = new User("user@.mail.ru", "Login", "B", LocalDate.of(1980, 12, 1));
        assertTrue(UserValidator.validateUser(goodUser));

        User userWithEmptyEmail = new User("", "L", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> UserValidator.validateUser(userWithEmptyEmail));

        User userWithoutEmailSymbol = new User("aj.ms.ru", "L", "N", LocalDate.of(1980, 12, 1));
        userWithoutEmailSymbol.setBirthday(LocalDate.of(1980, 2, 2));
        assertThrows(ValidationException.class, () -> UserValidator.validateUser(userWithoutEmailSymbol));

        User userWithWhiteSpacesInLogin = new User("user@mail.ru", "L N", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> UserValidator.validateUser(userWithWhiteSpacesInLogin));

        User userWithEmptyLogin = new User("user@mail.ru", "", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> UserValidator.validateUser(userWithEmptyLogin));

        User userWithFutureBirthDate = new User("user@mail.ru", "L", "N", LocalDate.of(2025, 12, 1));
        assertThrows(ValidationException.class, () -> UserValidator.validateUser(userWithFutureBirthDate));

    }
}
