package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    private List<Genre> genres;
    private Mpa mpa;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public Film() {
    }

    public Film(Long id, String name, String description, List<Genre> genres, Mpa mpa, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genres = genres;
        this.mpa = mpa;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, List<Genre> genres, Mpa mpa, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.genres = genres;
        this.mpa = mpa;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
