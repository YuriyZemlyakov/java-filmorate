package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.dtoMappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return filmService.getAllFilms().stream()
                .sorted(new IdFilmComparator())
                .collect(Collectors.toList());
    }
    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable long filmId) {
        return filmService.getFilmById(filmId);
    }


    @GetMapping("/popular")
    public Collection<Film> getMostLikedFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.getMostLikedFilmes(count);
    }





    @PostMapping
    public Film create(@RequestBody FilmRequestDto newFilm) {
        return filmService.addFilm(newFilm);
    }

    @PutMapping
    public Film update(@RequestBody FilmRequestDto editedFilm) {
        return filmService.updateFilm(editedFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/delete/{filmId}")
    public void deleteFilm( @PathVariable long filmId) {
        filmService.deleteFilm(filmId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }
}


