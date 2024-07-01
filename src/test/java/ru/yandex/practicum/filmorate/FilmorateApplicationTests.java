package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class FilmorateApplicationTests {

    @Test
    public void validateFilmTest() {
        FilmController filmController = new FilmController();
        Film goodFilm = new Film("T", "Drama", LocalDate.of(2000, 1, 1), 200);

        Film filmWithoutName = new Film("", "Drama", LocalDate.of(2000, 1, 1), 200);
        assertThrows(ValidationException.class, () -> filmController.validateFilm(filmWithoutName));

        Film filmWithTooLongDescription = new Film("T", "Drama11111111111111111111111111111111111111111111wwwww" +
                "2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222" +
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "ddddddddddddddddkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", LocalDate.of(2000, 1, 1), 200);
        assertThrows(ValidationException.class, () -> filmController.validateFilm(filmWithTooLongDescription));

        Film filmWithWrongDate = new Film("T", "Drama", LocalDate.of(1725, 1, 1), 200);
        assertThrows(ValidationException.class, () -> filmController.validateFilm(filmWithWrongDate));

        Film filmWithNegativeDuration = new Film("T", "Drama", LocalDate.of(2000, 1, 1), (-2));
        assertThrows(ValidationException.class, () -> filmController.validateFilm(filmWithNegativeDuration));
    }

    @Test
    public void validateUserTest() {
        UserController userController = new UserController();
        String errorMessage = null;
        User goodUser = new User("user@.mail.ru", "Login", "B", LocalDate.of(1980, 12, 1));
        assertTrue(userController.validateUser(goodUser));

        User userWithEmptyEmail = new User("", "L", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> userController.validateUser(userWithEmptyEmail));

        User userWithoutEmailSymbol = new User("aj.ms.ru", "L", "N", LocalDate.of(1980, 12, 1));
        userWithoutEmailSymbol.setBirthday(LocalDate.of(1980, 2, 2));
        assertThrows(ValidationException.class, () -> userController.validateUser(userWithoutEmailSymbol));

        User userWithWhiteSpacesInLogin = new User("user@mail.ru", "L N", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> userController.validateUser(userWithWhiteSpacesInLogin));

        User userWithEmptyLogin = new User("user@mail.ru", "", "N", LocalDate.of(1980, 12, 1));
        assertThrows(ValidationException.class, () -> userController.validateUser(userWithEmptyLogin));

        User userWithFutureBirthDate = new User("user@mail.ru", "L", "N", LocalDate.of(2025, 12, 1));
        assertThrows(ValidationException.class, () -> userController.validateUser(userWithFutureBirthDate));

    }
}
