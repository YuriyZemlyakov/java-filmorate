package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("inMemoryFilmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
        films.remove(filmId);

    }

    @Override
    public Film update(Film editedFilm) {
        log.info("Загружаем и валидируем внесенные изменения");
        if (editedFilm.getId() == null) {
            throw new ValidationException("Для обновления данных фильма id должен быть указан");
        }
        if (!films.containsKey(editedFilm.getId())) {
            log.error("Указан несуществующий id");
            throw new NotFoundException("Фильм с таким id не найден");
        }

        log.info("Сохраняем обновленные данные");
        Film oldFilm = films.get(editedFilm.getId());
        if (editedFilm.getName() != null) {
            oldFilm.setName(editedFilm.getName());
        }
        if (editedFilm.getDescription() != null) {
            oldFilm.setDescription(editedFilm.getDescription());
        }
        if (editedFilm.getReleaseDate() != null) {
            oldFilm.setReleaseDate(editedFilm.getReleaseDate());
        }
        if (editedFilm.getDuration() != 0) {
            oldFilm.setDuration(editedFilm.getDuration());
        }
        oldFilm.setLikes(editedFilm.getLikes());
        return oldFilm;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
        return films.get(filmId);
    }

    @Override
    public Long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
