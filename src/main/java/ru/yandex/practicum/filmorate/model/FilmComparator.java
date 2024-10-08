package ru.yandex.practicum.filmorate.model;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        return (o1.getLikes().size() - o2.getLikes().size());
    }
}
