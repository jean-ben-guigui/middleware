-- Creation des tables pour le projet

-- DROP TABLE Users
-- DROP TABLE reservation
-- DROP TABLE Events

CREATE TABLE Users(
	idUsers int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name varchar(200) not null,
	password varchar(200) not null,
	email varchar(200) not null,
	type varchar(10),
	primary key(idUsers));

CREATE TABLE Events(
	idEvents int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	artistname varchar(200) not null,
	date TIMESTAMP not null,
	category varchar(200) not null,
	primary key(idEvents));

CREATE TABLE reservation(
	idReservation int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	idevent int not null,
	iduser int not null,
	siege int not null,
	category varchar(10) not null,
	state varchar(200) not null,
	dateRes TIMESTAMP,
	primary key(idReservation),
	constraint ID_EVENTS_MATCH FOREIGN KEY(idevent) REFERENCES Events(idEvents),
	constraint ID_USERS_MATCH FOREIGN KEY(iduser) REFERENCES Users(idUsers));
	
-- DROP TRIGGER deletePending

CREATE TRIGGER DELETEPENDING 
AFTER INSERT ON RESERVATION 
 FOR EACH ROW MODE DB2SQL 
DELETE FROM Reservation WHERE State = 'pending' AND {fn TIMESTAMPDIFF( SQL_TSI_MINUTE, DATERES,CURRENT_TIMESTAMP)} > 5


--REMPLISSAGE DES TABLES USERS ET EVENTS

--Comptes administrateurs :
INSERT INTO Users (name, password, email, type) VALUES
('Arthur','azerty','arthur@gmail.com','client');
INSERT INTO Users (name, password, email, type) VALUES
('Arnaud','1234','arnaud@gmail.com','client');
INSERT INTO Users (name, password, email, type) VALUES
('Fabien','azerty','fabien@gmail.com','client');
INSERT INTO Users (name, password, email, type) VALUES
('Laureen','1234','laureen@gmail.com','client');
INSERT INTO Users (name, password, email, type) VALUES
('Julien','julien','julien@gmail.com','client');

--Comptes utilisateurs :
INSERT INTO Users (name, password, email, type) VALUES
('admin1','admin1','admin1@gmail.com','admin');
INSERT INTO Users (name, password, email, type) VALUES
('Robert','admin','admin2@gmail.com','admin');

--Les events
INSERT INTO Events (artistname, date, category) VALUES
('Les Frero Delavega','2017-03-30 20:00:00','C1');
INSERT INTO Events (artistname, date, category) VALUES
('N.E.R.D','2017-04-12 15:30:00','C2');
INSERT INTO Events (artistname, date, category) VALUES
('Plastic Bertrand','2017-05-26 10:45:00','C3');
INSERT INTO Events (artistname, date, category) VALUES
('Disney, le musical','2017-06-07 14:00:00','C4');
