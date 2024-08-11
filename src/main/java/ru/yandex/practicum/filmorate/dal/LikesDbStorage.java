package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class LikesDbStorage {
    private final JdbcTemplate jdbc;

    public LikesDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addLike(long filmId, long userId) {
        String query = "INSERT INTO likes(film_id, user_id) VALUES(?,?)";
        jdbc.update(query, filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        String query = "DELETE FROM likes where film_id = ? AND user_id = ?";
        jdbc.update(query, filmId, userId);
    }
}
