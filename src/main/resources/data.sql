MERGE INTO genre KEY(ID) VALUES (1,'Комедия');
MERGE INTO genre KEY(ID) VALUES (2,'Драма');
MERGE INTO genre KEY(ID) VALUES (3,'Мультфильм');
MERGE INTO genre KEY(ID) VALUES (4,'Триллер');
MERGE INTO genre KEY(ID) VALUES (5,'Документальный');
MERGE INTO genre KEY(ID) VALUES (6,'Боевик');
MERGE INTO mpa(id,name,description) VALUES (1,'G', 'Нет возрастных ограничений');
MERGE INTO mpa(id,name,description) VALUES (2,'PG', 'Детям фильм рекомендуется смотреть в присутствии взрослых');
MERGE INTO mpa(id,name,description) VALUES (3,'PG-13', 'Детям до 13 просмотр не желателен');
MERGE INTO mpa(id,name,description) VALUES (4,'R', 'Детям до 17 рекомендуется смотреть в присутствии взрослых');
MERGE INTO mpa(id,name,description) VALUES (5,'NC-17', 'Лицам до 18 просмотр запрещен');
MERGE INTO friendship_type(id,name) VALUES (1,'follower');
MERGE INTO friendship_type(id,name) VALUES (2,'friend');
