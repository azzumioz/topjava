DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, datetime, description, calories, user_id)
VALUES (0, TIMESTAMP '2015-06-01 14:00:00', 'Админ ланч', 510, 100001),
       (1, TIMESTAMP '2015-06-01 21:00:00', 'Админ ужин', 1500, 100001),
       (2, TIMESTAMP '2019-06-01 10:00:00', 'ланч', 500, 100000),
       (3, TIMESTAMP '2019-06-01 20:00:00', 'ужин', 1000, 100000)