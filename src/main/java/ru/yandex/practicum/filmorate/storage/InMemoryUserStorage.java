package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("inMemoryUserStorage")
@Slf4j
@Getter
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User getUser(Long userId) {
        return users.get(userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User add(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);

    }

    @Override
    public User update(User editedUser) {
        log.info("Загружаем и валидируем внесенные изменения");
        if (editedUser == null) {
            throw new ValidationException("Для обновления данных пользователя id должен быть указан");
        }
        if (!users.containsKey(editedUser.getId())) {
            log.error("Указан несуществующий id");
            throw new NotFoundException("Юзер с таким id не найден");
        }
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

    @Override
    public Long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
