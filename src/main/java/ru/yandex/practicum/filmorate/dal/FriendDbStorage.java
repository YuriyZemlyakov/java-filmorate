package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("FriendsDbStorage")
public class FriendDbStorage {
    private final JdbcTemplate jdbc;

    public FriendDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addFriend(long userId, long friendId, int friendshipType) {
        String query = "INSERT INTO friends(user_id, friend_id, friendship_type) VALUES(?,?,?)";
        jdbc.update(query, userId, friendId, friendshipType);
        if (friendshipType == 2) {
            String query2 = "UPDATE friends SET friendship_type = 2 WHERE user_id = ? AND friend_id = ?";
            jdbc.update(query2, friendId, userId);
        }
    }

    public void deleteFriend(long userId, long friendId, int friendshipType) {
        String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbc.update(query, userId, friendId);
        if (friendshipType == 2) {
            String query2 = "UPDATE friends SET friendship_type = 1 WHERE user_id = ? AND friend_id = ?";
            jdbc.update(query2, friendId, userId);
        }
    }
}
