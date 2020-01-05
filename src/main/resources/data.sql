insert into users(password, email, creation_time, is_active)
values ( '123', 'z@z.z', '2019-01-01 00:00:00', true),
       ( '123', 'a@a.a', '2019-01-01 00:00:00', true),
       ( '123', 'b@b.b', '2019-01-01 00:00:00', true),
       ( '123', 'c@c.c', '2019-01-01 00:00:00', true)
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
insert into tgroup_members(groups_id, members_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 3)
;

