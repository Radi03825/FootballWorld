-- INSERT INTO users (id, email, first_name, is_active, last_name, password, username)
-- VALUES (4, 'test@test.com', 'Test1', 0, 'Test1', '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2', 'Test');

-- user roles
INSERT INTO user_roles (id, `user_role`)
VALUES (1, 'ADMIN'),
       (2, 'MODERATOR'),
       (3, 'USER');

-- some users
INSERT INTO users (id, first_name, last_name, email, username, password)
VALUES (1, 'Admin', 'Adminov', 'admin@admin.com', 'Admin', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d'),
       (2, 'Moderator', 'Moderatorov', 'moderator@moderator.com', 'Moderator', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d'),
       (3, 'User', 'Userov', 'user@user.com', 'User', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d');

-- user roles
-- admin
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3);
-- moderator
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (2, 2),
       (2, 3);
-- user
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (3, 3);

--stadiums
INSERT INTO stadiums (id, `address`, `capacity`, `established`, `image_url`, `name`)
VALUES (1, 'London 100', '1000.00', '2021-07-01', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Stamford_Bridge_Clear_Skies.JPG/1200px-Stamford_Bridge_Clear_Skies.JPG', 'Stamford Bridge');

--teams
INSERT INTO teams (id, `badge_image_url`, `established`, `name`, `stadium_id`)
VALUES (1, 'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png', '2020-06-29', 'Chelsea', '1');

--skills
INSERT INTO skills (id, `defending`, `pace`, `passing`, `shooting`)
VALUES (1, '70', '73', '87', '71');

--players
INSERT INTO players (id, `birthdate`, `description`, `first_name`, `height`, `image_url`, `last_name`, `position`, `preferred_foot`, `price`, `manager_id`, `skills_id`, `team_id`)
VALUES (1, '2002-06-30', 'dddddddddd', 'Davide', '196', 'https://x9n7d4x6.rocketcdn.me/wp-content/uploads/2022/01/us-sassuolo-davide-frattesi.jpg.webp', 'Fratezzi', 'MIDFIELDER', 'RIGHT', '1233.00', '1', '1', '1');
