package ru.yandex.practicum.filmorate.dal;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("dbUserStorage")
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper;

    public UserDbStorage(JdbcTemplate jdbc, UserRowMapper userRowMapper) {
        this.jdbc = jdbc;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public User add(User user) {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                    .withTableName("users")
                    .usingGeneratedKeyColumns("id");
            Map<String,Object> values = new HashMap<>();
            values.put("login",user.getLogin());
            values.put("name", user.getName());
            values.put("email", user.getEmail());
            values.put("birthdate", user.getBirthday());
            Long userId = simpleJdbcInsert.executeAndReturnKey(values).longValue();
            user.setId(userId);
            return getUser(userId);
    }

    @Override
    public void delete(Long userId) {
            String sqlQuery = "DELETE FROM users WHERE id = ?";
            jdbc.update(sqlQuery, userId);

    }

    @Override
    public User update(User editedUser) {
        String query  = "update users set " +
                "login = ?, name = ?, email = ?, birthdate = ? WHERE id = ?";
        jdbc.update(query, editedUser.getLogin(),editedUser.getName(), editedUser.getEmail(),
                editedUser.getBirthday(),editedUser.getId());
        return getUser(editedUser.getId());
    }

    @Override
    public User getUser(Long userId) {
        User user = new User();
        String queryForUser = "SELECT * FROM users WHERE ID = ?";
        String queryForFriends = "SELECT user_id FROM friends WHERE friend_id = ?";
        user = jdbc.queryForObject(queryForUser,userRowMapper,userId);
        Collection<Long> friends = jdbc.queryForList(queryForFriends,Long.class,userId );
        user.setFriends(new HashSet<>(friends));
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        String query = "SELECT ID FROM users";
        return jdbc.queryForList(query, Long.class).stream()
                .map(userId -> getUser(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Long getNextId() {
        return null;
    }
}
