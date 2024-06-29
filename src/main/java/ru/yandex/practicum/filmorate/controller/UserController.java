package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<Long, User> users = new HashMap<>();
    Logger log = LoggerFactory.getLogger(User.class);

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("Загружаем и валидируем нового юзера");
        validateUser(newUser);
        newUser.setId(getNextId());
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }
        log.info("Записи присвоен id {}", newUser.getId());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PutMapping
    public User update(@RequestBody User editedUser) {
        log.info("Загружаем и валидируем внесенные изменения");
        if (editedUser == null) {
            throw new ValidationException("Для обновления данных пользователя id должен быть указан");
        }
        if (!users.containsKey(editedUser.getId())) {
            log.error("Указан несуществующий id");
            throw new NotFoundException("Юзер с таким id не найден");
        }
        validateUser(editedUser);
        log.info("Сохраняем обновленные данные");
        User oldUser = users.get(editedUser.getId());
        if (editedUser.getName() != null) {
            oldUser.setName(editedUser.getName());
        }
        if (editedUser.getEmail() != null) {
            oldUser.setEmail(editedUser.getEmail());
        }
        if (editedUser.getBirthday() != null) {
            oldUser.setBirthday(editedUser.getBirthday());
        }
        oldUser.setLogin(editedUser.getLogin());
        return oldUser;
    }

    public boolean validateUser(User user) {
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

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
