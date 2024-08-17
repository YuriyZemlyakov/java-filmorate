package ru.yandex.practicum.filmorate.dtoMappers;

import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

public class GenreMapper {
    public static GenreDto genreToDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        return dto;
    }
}
