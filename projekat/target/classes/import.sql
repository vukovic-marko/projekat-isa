
-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123

--INSERT INTO USER (id, username, password, first_name, last_name, email, enabled, last_password_reset_date) VALUES (2, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Nikolic', 'admin@example.com', true, '2017-10-01 18:57:58.508-07');
--INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('1', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'admin','1');
--INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('3', true, 's', 'admmin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'racadmin','1');

--INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
--INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_SYSTEM_ADMIN');
--INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_AIRPORT_ADMIN');
--INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_HOTEL_ADMIN');
--INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_RENT_A_CAR_ADMIN');


--INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('2', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'milorad','1');
--INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
--INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
--INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 5);

--INSERT INTO destination (id,city,country) VALUES(1,'novi sad','srbija');




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

INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('4', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin','1');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('5', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin1','1');
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4,4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5,4);

---
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('6', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin2','1');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('7', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin3','1');
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6,4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7,4);
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('8', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin4','1');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('9', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin5','1');
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8,4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9,4);
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('10', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin6','1');
INSERT INTO user (id, activated, city, email, first_name, last_name, password, phone, username,version) VALUES ('11', true, 's', 'admin@admin.admin', 's', 'ss', '$2a$10$BjZ8Ihb955YG9AiR4JvkL.k9WUR7/oGa8LIF6Q0ys7k.EVrbOtRqe', '123/456-78-99', 'hoteladmin7','1');
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10,4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (11,4);
 


INSERT INTO DESTINATION (id, city, country) VALUES (1, 'Novi Sad', 'Srbija');
INSERT INTO DESTINATION (id, city, country) VALUES (2, 'Beograd', 'Srbija');
--
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (3, 'Sekspirova 36', 'Hotel #3', 'Novi hotel', 6, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (4, 'Strazilovska 2', 'Hotel #4', 'Novi hotel', 7, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (5, 'Balzakova 36', 'Hotel #5', 'Novi hotel', 8, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (6, 'Kisacka 2', 'Hotel #6', 'Novi hotel', 9, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (7, 'Temerinska 36', 'Hotel #7', 'Novi hotel', 10, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (8, 'Svetozara Miletica 2', 'Hotel #8', 'Novi hotel', 11, 1);


INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (1, 'Narodnog fronta 25', 'Hotel #1', 'Novi hotel', 4, 1);
INSERT INTO HOTEL (id, address, name, promo_description, admin_id, address_id) VALUES (2, 'Ulica br. 2', 'Hotel #2', 'Novi hotel', 5, 1);
INSERT INTO HOTEL_ROOM (id, floor_number, room_number, size, hotel_id) VALUES (1, 1, '101', 1, 1);
INSERT INTO HOTEL_ROOM (id, floor_number, room_number, size, hotel_id) VALUES (2, 1, '101', 1, 2);
INSERT INTO HOTEL_ROOM (id, floor_number, room_number, size, hotel_id) VALUES (3, 1, '102', 1, 1);
INSERT INTO HOTEL_ADDITIONAL_SERVICE (id, name, price, hotel_id) VALUES (1, 'Wi-Fi', 10, 1);
INSERT INTO HOTEL_ADDITIONAL_SERVICE (id, name, price, hotel_id) VALUES (2, 'Wi-Fi', 15, 2);
INSERT INTO HOTEL_ROOM_PRICE(id, start_date, end_date, price, hotel_room_id) VALUES (1, '2018-01-01', '2018-05-31', 200.00, 1);
INSERT INTO HOTEL_ROOM_PRICE(id, start_date, end_date, price, hotel_room_id) VALUES (2, '2018-06-01', '2018-08-31', 300.00, 1);
INSERT INTO HOTEL_ROOM_PRICE(id, start_date, end_date, price, hotel_room_id) VALUES (3, '2018-09-01', '2018-12-31', 250.00, 1);

INSERT INTO HOTEL_RESERVATION(id, date_of_arrival, date_of_departure, number_of_guests, user_id) VALUES (1, '2018-12-24', '2018-12-28', 1, 3);
INSERT INTO HOTEL_ROOM_OCCUPATION(reservation_id, room_id) VALUES (1, 1);




INSERT INTO rentacar_company (id,address,description,name,version,admin,location_id) VALUES (1,'kisacka 24','ad','ad',1,3,1);


INSERT INTO car (id,brand,doors,model,name,price,seats,transmission,type,year,company_id) VALUES (1,'marka',2,'modle','naziv',345,5,'Manuelni','Hečbek',2000,1);

INSERT INTO car (id,brand,doors,model,name,price,seats,total_price,transmission,type,year,company_id) VALUES (1,'q',0,'q','q',23,5,0,'Manuelni','Hečbek',2000,1);
INSERT INTO car (id,brand,doors,model,name,price,seats,total_price,transmission,type,year,company_id) VALUES (2,'qe',3,'qee','qe',233,5,0,'Manuelni','Hečbek',2000,1);
INSERT INTO car (id,brand,doors,model,name,price,seats,total_price,transmission,type,year,company_id) VALUES (3,'qeer',3,'qee','qeer',233,5,0,'Manuelni','Hečbek',2000,1);

INSERT INTO car_reservation (id,end_date,price,start_date,car_id,end_destination_id,start_destination_id,user_id) VALUES (1,'2019-02-06',11,'2019-02-01',1,1,1,1);
INSERT INTO car_reservation (id,end_date,price,start_date,car_id,end_destination_id,start_destination_id,user_id) VALUES (2,'2019-02-05',11,'2019-02-01',2,1,1,1);
INSERT INTO car_reservation (id,end_date,price,start_date,car_id,end_destination_id,start_destination_id,user_id) VALUES (3,'2019-02-04',15,'2019-02-01',3,1,1,1);
