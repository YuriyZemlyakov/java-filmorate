package ru.yandex.practicum.filmorate.dal;

import org.hibernate.JDBCException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository("dbFilmStorage")
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper filmRowMapper;
    private final GenreRowMapper genreRowMapper;


    public FilmDbStorage(JdbcTemplate jdbc, FilmRowMapper filmRowMapper, GenreRowMapper genreRowMapper) {
        this.jdbc = jdbc;
        this.filmRowMapper = filmRowMapper;
        this.genreRowMapper = genreRowMapper;
    }


    @Override
    public Film add(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String,Object> values = new HashMap<>();
        values.put("name",film.getName());
        values.put("description", film.getDescription());
        if(film.getMpa() != null) {
            values.put("mpa_id", film.getMpa().getId());
        }
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        Long filmId = simpleJdbcInsert.executeAndReturnKey(values).longValue();
        film.setId(filmId);
        String queryForGenre = "MERGE INTO filmgenres(film_id, genre_id) VALUES(?,?)";
            if (film.getGenres() != null) {
                film.getGenres().stream()
                        .forEach(genre -> jdbc.update(queryForGenre, filmId, genre.getId()));
            }

        System.out.println(film.getGenres());
        return film;

    }
    @Override
    public Film getFilm(Long filmId) {
        Film film  = new Film();
        String queryForFilm = "SELECT * FROM films WHERE ID = ?";
        String queryForLikes = "SELECT user_id FROM likes WHERE film_id = ?";
        try {
            film = jdbc.queryForObject(queryForFilm, filmRowMapper, filmId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        Collection<Long> likes = jdbc.queryForList(queryForLikes,Long.class,filmId);
        film.setLikes(new HashSet<>(likes));
        String queryForGenres = "SELECT g.id, g.name FROM filmGenres fg JOIN genre g ON fg.genre_id = g.id " +
                "WHERE fg.film_id = ?";
        List<Genre> genresList = jdbc.query(queryForGenres, genreRowMapper, filmId);
        film.setGenres(genresList);
        return film;
    }
    @Override
    public Collection<Film> getAllFilms() {
        String query = "SELECT id FROM films";
        return jdbc.queryForList(query, Long.class).stream()
                .map(filmId ->getFilm(filmId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long filmId) {
        String query = "DELETE FROM films WHERE id = ?";
        jdbc.update(query, filmId);

    }

    @Override
    public Film update(Film editedFilm) {
        String query = "UPDATE films SET name = ?, description = ?, mpa_id = ?, duration = ?, release_date = ? " +
                "WHERE id = ?";
        jdbc.update(query, editedFilm.getName(), editedFilm.getDescription(),
                editedFilm.getMpa().getId(), editedFilm.getDuration(), editedFilm.getReleaseDate(), editedFilm.getId());
        String queryForGenreUpdate = "MERGE INTO filmgenres(film_id, genre_id) VALUES(?,?)";
        if(editedFilm.getGenres() != null) {
            editedFilm.getGenres().stream()
                    .forEach(genre -> jdbc.update(queryForGenreUpdate, editedFilm.getId(), genre.getId()));
        }
        return getFilm(editedFilm.getId());
    }

    @Override
    public Long getNextId() {
        return null;
    }


}
