DELETE FROM user_role;
DELETE FROM users;

INSERT INTO users(id, active, name, password, username)
VALUES (1, true, 'john', '$2a$10$jLqnf5VkWbSnhkuR7TahDOzWcO0yXDfjTK2aoX', 'user'),
       (2, true, 'jack', '$2a$10$jLqnf5VkWbSnhkuR7TahDOzWcO0yXDfjTK2aoX', 'mail@mail.com');

INSERT INTO user_role(user_id, roles)
VALUES (1, 'USER'), (1, 'ADMIN'),
       (2, 'USER');