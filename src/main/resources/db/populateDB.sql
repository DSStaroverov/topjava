DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(user_id, description,date_time, calories) VALUES
(100000,'lunch','2019-02-24 14:00:00',500),
(100000,'dinner','2019-02-24 19:00:00',800),
(100000,'breakfast','2019-02-24 09:00:00',600),
(100000,'lunch','2019-02-23 14:00:00',900),
(100000,'dinner','2019-02-23 19:00:00',1000),
(100000,'breakfast','2019-02-23 09:00:00',300),
(100001,'lunch_admin','2019-02-23 14:00:00',900),
(100001,'dinner_admin','2019-02-23 19:00:00',1000),
(100001,'breakfast_admin','2019-02-23 09:00:00',300);