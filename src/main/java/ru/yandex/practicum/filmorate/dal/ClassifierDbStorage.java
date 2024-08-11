package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ClassifierDbStorage")
public class ClassifierDbStorage {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper genreRowMapper;
    private final MpaRowMapper mpaRowMapper;

    public ClassifierDbStorage(JdbcTemplate jdbc, GenreRowMapper genreRowMapper, MpaRowMapper mpaRowMapper) {
        this.jdbc = jdbc;
        this.genreRowMapper = genreRowMapper;
        this.mpaRowMapper = mpaRowMapper;
    }
    public List<Genre> getAllGenres(){
        String query = "SELECT * FROM genre";
        List<Genre> genreList = jdbc.query(query, genreRowMapper);
        return genreList;
    }
    public Genre getGenreById(int genreId) {
        String query = "SELECT * FROM genre WHERE id = ?";
        return jdbc.queryForObject(query,genreRowMapper,genreId);
    }
    public Mpa getMpaById(int mpaId) {
        String query = "SELECT * FROM mpa WHERE id = ?";
        return jdbc.queryForObject(query,mpaRowMapper,mpaId);
    }
    public List<Mpa> getAllMpa() {
        String query = "SELECT * FROM mpa";
        return jdbc.query(query,mpaRowMapper);
    }

}
