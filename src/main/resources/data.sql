
-- user roles
INSERT INTO user_roles (id, `user_role`)
VALUES (1, 'USER'),
       (2, 'MODERATOR'),
       (3, 'ADMIN');

-- some users
INSERT INTO users (id, first_name, last_name, email, username, password)
VALUES (1, 'Admin', 'Adminov', 'admin@admin.com', 'Admin', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d'),
       (2, 'Moderator', 'Moderatorov', 'moderator@moderator.com', 'Moderator', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d'),
       (3, 'User', 'Userov', 'user@user.com', 'User', '7dfefc5d78590bca06ff7761810aaa403df126fd96c8d3f9831440b941fc81fbe5dc557e8a53ec8d');

-- user roles:
-- admin
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3);

-- moderator
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (2, 1),
       (2, 2);

-- user
INSERT INTO users_user_roles (`user_entity_id`, `user_roles_id`)
VALUES (3, 1);

-- stadiums
INSERT INTO stadiums (id, `address`, `capacity`, `established`, `image_url`, `name`)
VALUES (1, 'ul. "Republikanska", 8130 Tsentar, Sozopol', '3500.00', '2012-06-14', 'https://www.krupal.bg/storage/app/media/Projects/Sozopol%20Stadium/sozopol-stadium-slide2.jpg', 'Arena Sozopol'),
       (2, 'ul. "Mitko Palauzov", 5300 Gabrovo", 8130 Tsentar, Sozopol', '5500.00', '1951-03-17', 'http://photos.wikimapia.org/p/00/05/20/65/98_big.jpg', 'Stadion Hristo Botev'),
       (3, 'Bistritsa, Sofia, Bulgaria", 8130 Tsentar, Sozopol', '2500.00', '2017-06-04', 'https://www.europlan-online.de/files/d4c24d28bc680378c1f794cbba0cc41d.jpeg', 'Stadion Bistritsa'),
       (4, 'ul. "Balsha" 18, 1408 Ivan Vazov, Sofia', '2500.00', '1950-06-04', 'https://levskisofia.info/files/stadiums/288.jpg', 'Rakovski Stadium'),
       (5, 'ul. "Akademik Stefan Mladenov" 20, 3705 Vidin', '15000.00', '1920-03-04', 'https://secure.cache.images.core.optasports.com/soccer/venues/300x225/9877.jpg', 'Georgi Benkovski'),
       (6, 'ul. "Bulgaria" 77, 2850 Tsar Samuil, Petrich', '9500.00', '1920-03-04', 'https://matchez.today/cdn-cgi/image/w=720,h=405,fit=cover,f=auto/images/venues/12285.png', 'Stadion Tsar Samuil'),
       (7, 'ul. "Samoranska" 9, 2602 Gorna Mahala, Dupnica', '16000.00', '1952-01-07', 'https://pirinsport.com/sites/default/files/%D0%B1%D0%BE%D0%BD%D1%87%D1%83%D0%BA%20%D0%B4%D1%83%D0%BF%D0%BD%D0%B8%D1%86%D0%B0.jpg', 'Stadion Bonchuk'),
       (8, '2000 Tsentar, Samokov', '7000.00', '1972-03-08', 'https://levskisofia.info/files/stadiums/188.jpg', 'Stadion Iskar');

--teams
INSERT INTO teams (id, `badge_image_url`, `established`, `name`, `stadium_id`)
VALUES (1, 'https://upload.wikimedia.org/wikipedia/en/thumb/3/3a/FCSozopol_logo.png/225px-FCSozopol_logo.png', '2008-06-03', 'FC Sozopol', '1'),
       (2, 'https://upload.wikimedia.org/wikipedia/en/3/33/FC_Yantra_Gabrovo.png', '1919-02-05', 'FC Yantra Gabrovo', '2'),
       (3, 'https://upload.wikimedia.org/wikipedia/en/thumb/2/23/FC_Vitosha_Bistritsa_emblem.png/180px-FC_Vitosha_Bistritsa_emblem.png', '1958-05-15', 'FC Vitosha Bistritsa', '3'),
       (4, 'https://levskirakovski.bg/wp-content/themes/rakovski/images/levski-rakovski-logo.svg', '1913-07-15', 'Levski Rakovski', '4'),
       (5, 'https://upload.wikimedia.org/wikipedia/en/2/2b/Bdin_vidin_logo.png', '1923-04-15', 'Bdin Vidin', '5'),
       (6, 'https://upload.wikimedia.org/wikipedia/en/f/f9/Belasitsa_Petrich.png', '1923-02-10', 'OFC Belasitsa Petrich', '6'),
       (7, 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Marekold3.png/180px-Marekold3.png', '1947-05-11', 'FC Marek Dupnitsa', '7'),
       (8, 'https://upload.wikimedia.org/wikipedia/en/6/6a/Rilski-sportist_small.png', '1947-10-10', 'FC Rilski Sportist Samokov', '8');

--images
INSERT INTO images (id, url, public_id)
VALUES (1, "https://res.cloudinary.com/dgntwkoji/image/upload/v1659962665/etaasvpnphvtjya0ndpc.webp", "etaasvpnphvtjya0ndpc"),
       (2, "https://res.cloudinary.com/dgntwkoji/image/upload/v1659963210/ob4rfh0iwsqooqpdepb5.webp", "ob4rfh0iwsqooqpdepb5"),
       (3, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660028529/phjpdqyipr4i7pqmrk62.webp", "phjpdqyipr4i7pqmrk62"),
       (4, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660027258/uszbmvoniiaxsl3nku4e.webp", "uszbmvoniiaxsl3nku4e"),
       (5, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660027361/p9ixx384oxhnyof8thq1.webp", "p9ixx384oxhnyof8thq1"),
       (6, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660027287/ezerjwforvcmik4wpmee.webp", "ezerjwforvcmik4wpmee"),
       (7, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660027339/o9h2yunq00iuf05ozfi3.webp", "o9h2yunq00iuf05ozfi3"),
       (8, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660027314/hwkay8xe8dq5bob2uczz.webp", "hwkay8xe8dq5bob2uczz"),
       (9, "https://res.cloudinary.com/dgntwkoji/image/upload/v1659963395/kpqmbw6ftgzavf1afcaf.webp", "kpqmbw6ftgzavf1afcaf"),
       (10, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660028614/tcxasivi18ntbeq2ynuf.webp", "tcxasivi18ntbeq2ynuf"),
       (11, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660028636/n5o253um1clyggrfg0x8.webp", "n5o253um1clyggrfg0x8"),
       (12, "https://res.cloudinary.com/dgntwkoji/image/upload/v1660028558/yleva3aurpxna9b0vnbv.webp", "yleva3aurpxna9b0vnbv");

--skills
INSERT INTO skills (id, `defending`, `pace`, `passing`, `shooting`)
VALUES (1, '70', '73', '87', '71'),
       (2, '85', '75', '80', '21'),
       (3, '91', '53', '84', '31'),
       (4, '70', '63', '88', '50'),
       (5, '76', '73', '84', '61'),
       (6, '52', '75', '87', '55'),
       (7, '81', '73', '94', '63'),
       (8, '51', '83', '74', '68'),
       (9, '91', '73', '77', '64'),
       (10, '61', '97', '70', '38'),
       (11, '90', '53', '84', '78'),
       (12, '50', '94', '74', '28');

--players
INSERT INTO players (id, `birthdate`, `description`, `first_name`, `height`, `image_id`, `last_name`, `position`, `preferred_foot`, `price`, `manager_id`, `skills_id`, `team_id`)
VALUES (1, '2002-06-30', 'Really good young midfielder.', 'Stefan', '186', '1', 'Petrov', 'MIDFIELDER', 'RIGHT', '1500.00', '1', '1', '1'),
       (2, '2001-04-10', 'Really good young forward.', 'Teodor', '174', '2', 'Georgiev', 'FORWARD', 'RIGHT', '3000.00', '1', '2', '3'),
       (3, '2004-07-27', 'Really good young defender.', 'Ilian', '193', '3', 'Manolov', 'GOALKEEPER', 'RIGHT', '5000.00', '1', '3', '4'),
       (4, '2002-07-04', 'Really good young defender.', 'Kalin', '191', '4', 'Stefanov', 'DEFENDER', 'RIGHT', '2500.00', '1', '4', '3'),
       (5, '2003-02-24', 'Really good young defender.', 'Martin', '185', '5', 'Angelov', 'DEFENDER', 'RIGHT', '2000.00', '1', '5', '5'),
       (6, '2001-04-14', 'Really good young defender.', 'Konstantin', '182', '6', 'Vladigerov', 'MIDFIELDER', 'LEFT', '1000.00', '1', '6', '2'),
       (7, '1999-10-27', 'Really good young defender.', 'Darin', '177', '7', 'Petrov', 'FORWARD', 'RIGHT', '2500.00', '1', '7', '1'),
       (8, '2000-01-20', 'Really good young defender.', 'Kaloqn', '193', '8', 'Davidov', 'MIDFIELDER', 'RIGHT', '5000.00', '1', '8', '4'),
       (9, '2000-01-01', 'Really good young defender.', 'Steven', '196', '9', 'Ezze', 'DEFENDER', 'LEFT', '10000.00', '1', '9', '5'),
       (10, '2002-09-10', 'Really good young defender.', 'Ivan', '193', '10', 'Pavlov', 'GOALKEEPER', 'RIGHT', '2000.00', '1', '10', '7'),
       (11, '2001-04-28', 'Really good young defender.', 'David', '193', '11', 'Stefanov', 'FORWARD', 'RIGHT', '7000.00', '1', '11', '4'),
       (12, '2003-11-20', 'Really good young defender.', 'Antoan', '193', '12', 'Paskalev', 'GOALKEEPER', 'RIGHT', '5000.00', '1', '12', '8');
