insert into users(login, password, is_active, logged_in, email, creation_time, phone)
values (1, '123', false, false, 'z@z.z', '2019-01-01 00:00:00', '+00000000000'),
       (1, '123', true, false, 'a@a.a', '2019-01-01 00:00:00', '+48600111222'),
       (1, '123', true, false, 'b@b.b', '2019-01-01 00:00:00', '+48601222333'),
       (1, '123', true, false, 'c@c.c', '2019-01-01 00:00:00', '+48602333444')
;
insert into members(creation_time, is_active, nick, user_id)
values ('2019-01-01 00:00:00', true, 'a', 1),
       ('2019-01-01 00:00:00', true, 'b', 2),
       ('2019-01-01 00:00:00', true, 'c', 3)
;
insert into tgroup(creation_time, description, group_name, is_active, members_limit)
values ('2019-01-01 00:00:00', 'java junior developers', 'devmeet', true, 4),
       ('2019-01-01 00:00:00', 'python junior developers', 'snakes', true, 3)
;


