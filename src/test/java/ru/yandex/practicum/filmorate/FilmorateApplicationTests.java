package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
class FilmorateApplicationTests {

    //	@Test
//	void contextLoads() {
//	}
    @Test
    public void validateFilmTest() {
        FilmController filmController = new FilmController();
        Film goodFilm = new Film();
        goodFilm.setName("T");
        goodFilm.setDescription("Drama");
        goodFilm.setReleaseDate(LocalDate.of(2000, 1, 1));
        goodFilm.setDuration(200);
        assertTrue(filmController.validateFilm(goodFilm));

        Film filmWithoutName = new Film();
        filmWithoutName.setName("");
        filmWithoutName.setDescription("Drama");
        filmWithoutName.setReleaseDate(LocalDate.of(2000, 1, 1));
        filmWithoutName.setDuration(200);
        String errorMessage = null;
        try {
            filmController.validateFilm(filmWithoutName);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("Название фильма не может быть пустым", errorMessage);

        errorMessage = null;
        Film filmWithTooLongDescription = new Film();
        filmWithTooLongDescription.setName("T");
        filmWithTooLongDescription.setDescription("Drama11111111111111111111111111111111111111111111wwwww" +
                "2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222" +
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "ddddddddddddddddkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        filmWithTooLongDescription.setReleaseDate(LocalDate.of(2000, 1, 1));
        filmWithTooLongDescription.setDuration(200);
        try {
            filmController.validateFilm(filmWithTooLongDescription);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("Максимальная длина описания - 200 символов", errorMessage);

        errorMessage = null;
        Film filmWithWrongDate = new Film();
        filmWithWrongDate.setName("T");
        filmWithWrongDate.setDescription("Drama");
        filmWithWrongDate.setReleaseDate(LocalDate.of(1894, 8, 1));
        filmWithWrongDate.setDuration(200);
        try {
            filmController.validateFilm(filmWithWrongDate);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", errorMessage);

        errorMessage = null;
        Film filmWithNegativeDuration = new Film();
        filmWithNegativeDuration.setName("T");
        filmWithNegativeDuration.setDescription("Drama");
        filmWithNegativeDuration.setReleaseDate(LocalDate.of(1899, 8, 1));
        filmWithNegativeDuration.setDuration(-2);
        try {
            filmController.validateFilm(filmWithNegativeDuration);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("Продолжительность фильма должна быть положительным числом", errorMessage);

    }

    @Test
    public void validateUserTest() {
        UserController userController = new UserController();
        String errorMessage = null;
        User goodUser = new User();
        goodUser.setName("B");
        goodUser.setLogin("Login");
        goodUser.setEmail("user@.ru");
        goodUser.setBirthday(LocalDate.of(1980, 12, 1));
        assertTrue(userController.validateUser(goodUser));

        User userWithEmptyEmail = new User();
        userWithEmptyEmail.setName("T");
        userWithEmptyEmail.setLogin("L");
        userWithEmptyEmail.setEmail("");
        userWithEmptyEmail.setBirthday(LocalDate.of(1980, 2, 2));
        try {
            userController.validateUser(userWithEmptyEmail);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("email не может быть пустым", errorMessage);

        errorMessage = null;
        User userWithoutEmailSymbol = new User();
        userWithoutEmailSymbol.setName("T");
        userWithoutEmailSymbol.setLogin("L");
        userWithoutEmailSymbol.setEmail("sdfj.ru");
        userWithoutEmailSymbol.setBirthday(LocalDate.of(1980, 2, 2));
        try {
            userController.validateUser(userWithoutEmailSymbol);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("email должен содержать символ @", errorMessage);

        errorMessage = null;
        User userWithWhiteSpacesInLogin = new User();
        userWithWhiteSpacesInLogin.setName("T");
        userWithWhiteSpacesInLogin.setLogin("L W");
        userWithWhiteSpacesInLogin.setEmail("user@mail.ru");
        userWithWhiteSpacesInLogin.setBirthday(LocalDate.of(1980, 2, 2));
        try {
            userController.validateUser(userWithWhiteSpacesInLogin);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("логин не может содержать пробелы", errorMessage);

        errorMessage = null;
        User userWithEmptyLogin = new User();
        userWithEmptyLogin.setName("T");
        userWithEmptyLogin.setLogin("");
        userWithEmptyLogin.setEmail("user@mail.ru");
        userWithEmptyLogin.setBirthday(LocalDate.of(1980, 2, 2));
        try {
            userController.validateUser(userWithEmptyLogin);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("логин не может быть пустым", errorMessage);

        errorMessage = null;
        User userWithFutureBirthDate = new User();
        userWithFutureBirthDate.setName("T");
        userWithFutureBirthDate.setLogin("L");
        userWithFutureBirthDate.setEmail("user@mail.ru");
        userWithFutureBirthDate.setBirthday(LocalDate.of(2025, 2, 2));
        try {
            userController.validateUser(userWithFutureBirthDate);
        } catch (ValidationException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("дата рождения не может быть в будущем", errorMessage);
    }

}
