package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FriendDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    public UserService(UserStorage userStorage, FriendDbStorage friendDbStorage) {
        this.userStorage = userStorage;
        this.friendDbStorage = friendDbStorage;
    }

    public User addUser(User newUser) {
        log.info("Загружаем и валидируем нового юзера");
        UserValidator.validateUser(newUser);
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
    public void deleteUser(Long userId) {
        userNotNullValidate(userId);
        userStorage.delete(userId);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }
    public User getUser(long userId) {
        return userStorage.getUser((userId));
    }

    public void addFriend(Long userId, Long friendId) {
        userNotNullValidate(userId);
        userNotNullValidate(friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        int friendshipType = 1; //запрос на подписку
        if (user.getFriends().contains(friendId)) {
            friendshipType = 2;  //подтверждение дружбы
        }
        friendDbStorage.addFriend(userId,friendId,friendshipType);


    }

    public void deleteFriend(Long userId, Long friendId) {
        userNotNullValidate(userId);
        userNotNullValidate(friendId);
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (!user.getFriends().contains(friendId)) {
            throw new NotFoundException("Друга с таким id не найдено");
        }
        int friendshipType = 1;
        if (friend.getFriends().contains(userId)) {
            friendshipType = 2;
        }
        friendDbStorage.deleteFriend(userId,friendId,friendshipType);
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
