package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
public class ClassifierController {
    private final FilmService filmService;

    public ClassifierController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return filmService.getAllMpa();
    }
    @GetMapping("/mpa/{mpaId}")
    public Mpa getMpaById(@PathVariable int mpaId) {
        return filmService.getMpaById(mpaId);
    }
    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }
    @GetMapping("/genres/{genreId}")
    public Genre getGenreById(@PathVariable int genreId) {
        return filmService.getGenreById(genreId);
    }
}
