DELETE FROM genre;
DELETE FROM rating;
INSERT INTO genre(name) VALUES ('drama');
INSERT INTO genre(name) VALUES ('comedy');
INSERT INTO genre(name) VALUES ('cartoon');
INSERT INTO genre(name) VALUES ('thriller');
INSERT INTO genre(name) VALUES ('documentary');
INSERT INTO genre(name) VALUES ('action');
INSERT INTO mpa(name,description) VALUES ('G', 'Нет возрастных ограничений');
INSERT INTO mpa(name,description) VALUES ('PG', 'Детям фильм рекомендуется смотреть в присутствии взрослых');
INSERT INTO mpa(name,description) VALUES ('PG13', 'Детям до 13 просмотр не желателен');
INSERT INTO mpa(name,description) VALUES ('R', 'Детям до 17 рекомендуется смотреть в присутствии взрослых');
INSERT INTO mpa(name,description) VALUES ('NC17', 'Лицам до 18 просмотр запрещен');
INSERT INTO friendship_type(name) VALUES ('follower');
INSERT INTO friendship_type(name) VALUES ('friend');
