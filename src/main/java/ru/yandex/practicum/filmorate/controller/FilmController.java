package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/popular")
    public Collection<Film> getMostLikedFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.getMostLikedFilmes(count);
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        filmService.addFilm(newFilm);
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film editedFilm) {
        return filmService.updateFilm(editedFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }
}


