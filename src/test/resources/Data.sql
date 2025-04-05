
DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS roles_master;

Drop TABLE if EXISTS login;

Drop TABLE if EXISTS slots;

Drop TABLE if EXISTS profile;


CREATE TABLE roles_master (
  rolename varchar(255) NOT NULL,
  responsibilities varchar(255) DEFAULT NULL,
  PRIMARY KEY (rolename)
);
CREATE TABLE roles (
  rolename varchar(255) NOT NULL,
  responsibilities varchar(255) DEFAULT NULL,
  admin_mail_id varchar(50) DEFAULT NULL,
  id int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

CREATE TABLE profile (
  vehicle_number varchar(50) NOT NULL,
  phone_num varchar(20) DEFAULT NULL,
  user_name varchar(50) DEFAULT NULL,
  no_of_vehicles int DEFAULT '1',
  vehicle_type varchar(20) DEFAULT NULL,
  booking_date varchar(50) DEFAULT NULL,
  user_email_id varchar(50) DEFAULT NULL,
  paid_status tinyint(1) DEFAULT '0',
  paid_amount decimal(10,0) DEFAULT '0',
  allocated_slot_number varchar(50) DEFAULT NULL,
  parked_property_name varchar(50) DEFAULT NULL,
  duration_of_allocation varchar(20) DEFAULT NULL,
  payment_date varchar(20) DEFAULT NULL,
  admin_mail_id varchar(50) DEFAULT NULL,
  vehicle_model varchar(50) DEFAULT NULL,
  total_amount decimal(10,0) DEFAULT '0',
  booking_time varchar(50) DEFAULT NULL,
  is_banned tinyint(1) DEFAULT '0',
  fine_amount int DEFAULT '0',
  booking_source varchar(10) DEFAULT NULL,
  role_name varchar(10) DEFAULT NULL,
  vehicle_brand varchar(20) DEFAULT NULL,
  fuel_type varchar(20) DEFAULT NULL,
  vehicle_clr varchar(20) DEFAULT NULL,
  vehicle_gene varchar(20) DEFAULT NULL,
  endtime varchar(20) DEFAULT NULL,
  remainingtime varchar(20) DEFAULT NULL,
  PRIMARY KEY (vehicle_number)
);
CREATE TABLE login (
  email varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  rolename varchar(50) NOT NULL,
  active varchar(10) NOT NULL DEFAULT 'INACTIVE',
  remarks varchar(10) DEFAULT NULL,
  is_banned tinyint(1) DEFAULT NULL,
  PRIMARY KEY (email)
);
CREATE TABLE slots (
  slot_id int NOT NULL AUTO_INCREMENT,
  slot_number varchar(50) DEFAULT NULL,
  floor varchar(50) DEFAULT NULL,
  slot_availability tinyint(1) DEFAULT 1,
  vehicle_type varchar(50) DEFAULT NULL,
  admin_name varchar(50) DEFAULT NULL,
  admin_phone varchar(50) DEFAULT NULL,
  property_type varchar(50) DEFAULT NULL,
  admin_mail_id varchar(50) DEFAULT NULL,
  vehicle_num varchar(50) DEFAULT NULL,
  x int DEFAULT NULL,
  y int DEFAULT NULL,
  height int DEFAULT NULL,
  width int DEFAULT NULL,
  ranges varchar(20) DEFAULT NULL,
  hold tinyint(1) DEFAULT 0,
  hold_expiry_time varchar(20) DEFAULT NULL,
  start_time varchar(40) DEFAULT NULL,
  exit_time varchar(40) DEFAULT NULL,
  sheet_id varchar(20) DEFAULT NULL,
  city varchar(20) DEFAULT NULL,
  district varchar(20) DEFAULT NULL,
  state varchar(20) DEFAULT NULL,
  country varchar(20) DEFAULT NULL,
  property_name varchar(50) DEFAULT NULL,
  google_location varchar(250) DEFAULT NULL,
  PRIMARY KEY (slot_id)
);


INSERT INTO roles (rolename, responsibilities, admin_mail_id, id)
VALUES ('Auto Entry and Exit', 'automated barriers for entry and exit', 'gokulgnair777@gmail.com', 29);
INSERT INTO roles_master (rolename, responsibilities) VALUES
('Auto Entry and Exit', 'automated barriers for entry and exit'),
('Qr code Entry and Exit', 'without automated barriers for entry and exit');
INSERT INTO profile (
    vehicle_number, phone_num, user_name, no_of_vehicles, vehicle_type, booking_date,
    user_email_id, paid_status, paid_amount, allocated_slot_number, parked_property_name,
    duration_of_allocation, payment_date, admin_mail_id, vehicle_model, total_amount,
    booking_time, is_banned, fine_amount, booking_source, role_name, vehicle_brand,
    fuel_type, vehicle_clr, vehicle_gene, endtime, remainingtime
) VALUES (
    'KL65H432', '9947215650', 'Vijay', 1, 'car', '2025-04-02 02:30:00',
    'vijay007@gmail.com', true, 170, 'A1', 'College Of Engineering Chengannur',
    60, '2025-03-21', 'gokulgnair777@gmail.com', 'Civic', 170, '02:30:00',
    true, 0, NULL, NULL, 'Honda', 'Petrol', 'Red', 'Sedan', '2025-04-02T03:31', NULL
);

INSERT INTO slots (
  slot_id, slot_number, floor, slot_availability, vehicle_type, admin_name, admin_phone,
  property_type, admin_mail_id, vehicle_num, x, y, height, width, ranges, hold,
  hold_expiry_time, start_time, exit_time, sheet_id, city, district, state, country,
  property_name, google_location
) VALUES
(228, 'A1', 'Ground', false, 'Car', 'Hilal', '9645794547', 'Ground', 'gokulgnair777@gmail.com', 'kl65h432',
 250, 70, 40, 40, '1-4', false, NULL, '2025-03-21 02:25:00', '2025-03-21 03:25:00',
 '250874001847053', 'Chengannur', 'Alappuzha', 'Kerala', 'India',
 'College Of Engineering Chengannur', '9.317064431282477,76.6172428541319'),

(229, 'A2', 'Ground', true, 'Car', 'Hilal', '9645794547', 'Ground', 'gokulgnair777@gmail.com', NULL,
 300, 90, 40, 40, '1-4', false, NULL, NULL, NULL,
 '250874001847053', 'Chengannur', 'Alappuzha', 'Kerala', 'India',
 'College Of Engineering Chengannur', '9.317064431282477,76.6172428541319'),

(230, 'A3', 'Ground', true, 'Car', 'Hilal', '9645794547', 'Ground', 'gokulgnair777@gmail.com', NULL,
 350, 60, 40, 40, '1-4', false, NULL, NULL, NULL,
 '250874001847053', 'Chengannur', 'Alappuzha', 'Kerala', 'India',
 'College Of Engineering Chengannur', '9.317064431282477,76.6172428541319'),

(231, 'B1', 'Ground', true, 'Car', 'Hilal', '9645794547', 'Ground', 'gokulgnair777@gmail.com', NULL,
 400, 60, 40, 40, '1-4', false, NULL, NULL, NULL,
 '250594665435061', 'Chengannur', 'Alappuzha', 'Kerala', 'India',
 'College Of Engineering Chengannur', '9.317064431282477,76.6172428541319');


 INSERT INTO login (email, password, rolename, active, remarks, is_banned)
 VALUES ('gokulgnair777@gmail.com', 'Hilal@123', 'ADMIN_USER', 'ACTIVE', NULL, false);
