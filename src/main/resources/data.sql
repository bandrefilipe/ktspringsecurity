insert into users (username, password, enabled) values
    ('admin', 'admin', true),
    ('user', 'user', true),
    ('disabled', 'disabled', false);

insert into authorities (username, authority) values
    ('admin', 'ROLE_ADMIN'),
    ('admin', 'ROLE_USER'),
    ('user', 'ROLE_USER'),
    ('disabled', 'ROLE_USER');
