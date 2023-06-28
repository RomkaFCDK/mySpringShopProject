INSERT INTO users (id, name, password, email, role)
VALUES (1, 'admin', '$2a$10$QQjXtyM/.eDrIJJjeJZn5ONqTVEXGuFFT/URC9B9c0HyBvvtz30la', 'admin@gmail.com', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;