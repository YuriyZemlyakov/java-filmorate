package ru.yandex.practicum.filmorate.dtoMappers;

import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserDto userToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        Set<Friend> friends = new HashSet<>();
        user.getFriends().stream()
                .forEach(l -> friends.add(new Friend(l)));
        dto.setFriends(friends);
        return dto;
    }

    public static User dtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setBirthday(dto.getBirthday());
        Set<Long> friendsIdList = new HashSet<>();
        dto.getFriends().stream()
                .forEach(friend -> friendsIdList.add(friend.getId()));
        user.setFriends(friendsIdList);
        return user;
    }
}
