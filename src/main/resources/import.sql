INSERT INTO user(username, password, role, subscription_id) VALUES ('admin','$2a$10$/h6x4IPW4SLvNL2p2N6EvOOzLX66va20TvnR4edlKhdFRwOSZE71.', 'ROLE_ADMIN', 3);
INSERT INTO user(username, password, role, subscription_id) VALUES ('user','$2a$10$o.76NoIpMLz/K.m8VUOvuuAOTwH22qbgcme5DMpxqCbjt.FqewAqy', 'ROLE_USER', 3);
INSERT INTO subscription (name, file_limit) VALUES ('Starter', 50);
INSERT INTO subscription (name, file_limit) VALUES ('Advanced', 10000);
INSERT INTO subscription (name, file_limit) VALUES ('Pro', 15000000000);
