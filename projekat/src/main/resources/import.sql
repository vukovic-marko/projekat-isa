
-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123

--INSERT INTO USER (id, username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES (2, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Nikolic', 'admin@example.com', true, '2017-10-01 18:57:58.508-07');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('1', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'admin','1');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('3', true, 's', 'admmin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'racadmin','1');

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_SYSTEM_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_AIRPORT_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_HOTEL_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_RENT_A_CAR_ADMIN');


INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('2', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'milorad','1');
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 5);

INSERT INTO destination (id,city,country) VALUES(1,'novi sad','srbija');
INSERT INTO rentacar_company (id,address,description,name,version,admin,location_id) VALUES (1,'kisacka 24','ad','ad',1,3,1);

INSERT INTO car (id,brand,doors,model,name,price,seats,transmission,type,year,company_id) VALUES (1,'marka',2,'modle','naziv',345,5,'Manuelni','Hečbek',2000,1);
