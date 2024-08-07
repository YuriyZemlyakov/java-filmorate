package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Getter
@Setter
public class Film {
    private Long id;
    private String name;
    private String description;
    private String genre;
    private String mpa;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public Film() {
    }

    public Film(Long id, String name, String description, String genre, String mpa, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.mpa = mpa;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, String genre, String mpa, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.mpa = mpa;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
