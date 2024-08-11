package ru.yandex.practicum.filmorate.dtoMappers;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

public class MpaMapper {
    public static MpaDto mpaToDto(Mpa mpa) {
        MpaDto dto = new MpaDto();
        dto.setId(mpa.getId());
        return dto;
    }
}
