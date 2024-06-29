package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
}
