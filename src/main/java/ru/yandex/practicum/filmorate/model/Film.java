package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Film.
 */
@Getter
@Setter
public class Film {
    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
}
