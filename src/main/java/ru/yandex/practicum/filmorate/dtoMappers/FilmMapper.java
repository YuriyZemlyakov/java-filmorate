package ru.yandex.practicum.filmorate.dtoMappers;

import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.stream.Collectors;

public class FilmMapper {
    public static FilmRequestDto mapToFilmDto(Film film) {
        FilmRequestDto dto = new FilmRequestDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        dto.setMpa(MpaMapper.mpaToDto(film.getMpa()));
        List<Genre> genreList = film.getGenres();
        List<GenreDto> listDto = film.getGenres().stream()
                .map(g -> GenreMapper.genreToDto(g))
                .collect(Collectors.toList());
        dto.setGenres(listDto);
        return dto;
    }

    public static Film dtoToFilm(FilmRequestDto dto) {
        Film film = new Film();
        film.setId(dto.getId());
        film.setName(dto.getName());
        film.setDescription(dto.getDescription());
        film.setReleaseDate(dto.getReleaseDate());
        film.setDuration(dto.getDuration());
        Mpa mpa = new Mpa();
        if (dto.getMpa() != null) {
            mpa.setId(dto.getMpa().getId());
            film.setMpa(mpa);
        }
        List<GenreDto> genreDtoList = dto.getGenres();
        if (dto.getGenres() != null) {
            List<Genre> genreList = dto.getGenres().stream()
                    .map(d -> new Genre(d.getId(), null))
                    .collect(Collectors.toList());
            film.setGenres(genreList);
        }

        return film;

    }
}
