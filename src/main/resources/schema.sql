
CREATE TABLE IF NOT EXISTS genre (
id INTEGER PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS mpa  (
id INTEGER PRIMARY KEY,
name VARCHAR,
description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS films (
id INTEGER GENERATED BY DEFAULT AS IDENTITY  PRIMARY KEY,
name VARCHAR(255),
description VARCHAR,
mpa_id INTEGER REFERENCES mpa(id),
release_date DATE,
duration INTEGER
);


CREATE TABLE IF NOT EXISTS users (
id INTEGER GENERATED BY DEFAULT AS IDENTITY  PRIMARY KEY,
name VARCHAR(255),
login VARCHAR(255),
email VARCHAR(255) UNIQUE ,
birthdate DATE
);

CREATE TABLE IF NOT EXISTS friendship_type (
id INTEGER PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS friends (
id INTEGER GENERATED BY DEFAULT AS IDENTITY  PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
friend_id INTEGER REFERENCES users(id),
friendship_type INTEGER REFERENCES friendship_type(id)
);

CREATE TABLE IF NOT EXISTS filmGenres (
film_id INTEGER REFERENCES films(id),
genre_id INTEGER REFERENCES genre(id),
PRIMARY KEY (film_id, genre_id)
);



CREATE TABLE IF NOT EXISTS likes (
id INTEGER GENERATED BY DEFAULT AS IDENTITY  PRIMARY KEY,
film_id INTEGER references films(id),
user_id INTEGER references users(id)
);
