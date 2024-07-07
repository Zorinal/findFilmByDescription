DELETE FROM films;

insert into films(id, description, moderated, name, user_id) VALUES
    (1, 'first', false, 'drive', 1),
    (2, 'second', false, 'hola', 1),
    (3, 'third', false, 'hi', 1),
    (4, 'fourth', false, 'privet', 1);