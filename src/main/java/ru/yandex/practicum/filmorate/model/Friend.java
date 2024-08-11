package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friend {
    private long id;

    public Friend(long id) {
        this.id = id;
    }
}
