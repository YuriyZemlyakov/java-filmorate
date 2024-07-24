package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User newUser) {
        log.info("Загружаем и валидируем нового юзера");
        UserValidator.validateUser(newUser);
        newUser.setId(userStorage.getNextId());
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }
        log.info("Записи присвоен id {}", newUser.getId());
        userStorage.add(newUser);
        return newUser;
    }

    public User updateUser(User editedUser) {
        log.info("Валидируем данные пользователя");
        UserValidator.validateUser(editedUser);
        log.info("Сохраняем обновленные данные");
        return userStorage.update(editedUser);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(Long userId1, Long userId2) {
        userNotNullValidate(userId1);
        userNotNullValidate(userId2);
        User user1 = userStorage.getUser(userId1);
        User user2 = userStorage.getUser(userId2);
        if (user1.getFriends().contains(userId2)) {
            throw new NotFoundException("Друг с таким id уже добавлен");
        }
        user1.getFriends().add(userId2);

        if (user2.getFriends().contains(userId1)) {
            throw new NotFoundException("Друг с таким id уже добавлен");
        }
        user2.getFriends().add(userId1);


    }

    public void deleteFriend(Long userId1, Long userId2) {
        userNotNullValidate(userId1);
        userNotNullValidate(userId2);
        User user1 = userStorage.getUser(userId1);
        User user2 = userStorage.getUser(userId2);
        user1.getFriends().remove(userId2);
        user2.getFriends().remove(userId1);
    }

    public List<User> getFriends(long userId) {
        userNotNullValidate(userId);
        return userStorage.getUser(userId).getFriends().stream()
                .map(friendId -> userStorage.getUser(friendId))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long userId1, long userId2) {
        userNotNullValidate(userId1);
        userNotNullValidate(userId2);
        return userStorage.getUser(userId1).getFriends().stream()
                .filter(friendId -> userStorage.getUser(userId2).getFriends().contains(friendId))
                .map(userId -> userStorage.getUser(userId))
                .collect(Collectors.toList());

    }

    private void userNotNullValidate(long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }
    }


}
