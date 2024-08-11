package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dtoMappers.UserMapper;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> UserMapper.userToDto(user))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends")
    public Collection<Friend> getFriends(@PathVariable Long id) {
        return userService.getFriends(id).stream()
                .map(i -> new Friend(i))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<UserDto> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriends(id, otherId).stream()
                .map(user -> UserMapper.userToDto(user))
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto newUser) {
        return UserMapper.userToDto(userService.addUser(UserMapper.dtoToUser(newUser)));
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto editedUser) {
        return UserMapper.userToDto(userService.updateUser(UserMapper.dtoToUser(editedUser)));
    }

    @PutMapping("/{friendId}/friends/{id}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{friendId}/friends/{id}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
    }
}
