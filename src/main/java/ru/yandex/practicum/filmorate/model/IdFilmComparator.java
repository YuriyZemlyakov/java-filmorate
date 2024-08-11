package ru.yandex.practicum.filmorate.model;

import java.util.Comparator;

public class IdFilmComparator implements Comparator<Film> {

    @Override
    public int compare(Film o1, Film o2) {
        return o1.getId().intValue() - o2.getId().intValue();
    }
}
