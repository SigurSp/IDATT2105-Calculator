INSERT INTO users (username, password) VALUES ('admin', 'admin123');
INSERT INTO users (username, password) VALUES ('test', 'test123');

INSERT INTO calculations (expression, result, user_id) VALUES ('2 + 3', '5.0', 1);
INSERT INTO calculations (expression, result, user_id) VALUES ('10 - 4', '6.0', 1);
INSERT INTO calculations (expression, result, user_id) VALUES ('5 * 2', '10.0', 1);
INSERT INTO calculations (expression, result, user_id) VALUES ('8 / 2', '4.0', 1);
INSERT INTO calculations (expression, result, user_id) VALUES ('15 + 7', '22.0', 1);

INSERT INTO calculations (expression, result, user_id) VALUES ('3 + 4', '7.0', 2);
INSERT INTO calculations (expression, result, user_id) VALUES ('20 - 5', '15.0', 2);
INSERT INTO calculations (expression, result, user_id) VALUES ('6 * 3', '18.0', 2);
INSERT INTO calculations (expression, result, user_id) VALUES ('12 / 4', '3.0', 2);
INSERT INTO calculations (expression, result, user_id) VALUES ('9 + 1', '10.0', 2);