package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user);
    void delete(Long userId);
    User update(User editedUser);
    User getUser(Long userId);
    Collection<User> getAllUsers();
    Long getNextId();
}
