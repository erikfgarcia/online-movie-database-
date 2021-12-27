-- Student: Erik F Garcia
-- Project Phase 2
-- Instructor – Dr. Jessica Lin
-- Email: egarci4@masonlive.gmu.edu

DROP TABLE Movie CASCADE CONSTRAINTS;
DROP TABLE Member_ CASCADE CONSTRAINTS;
DROP TABLE Actor CASCADE CONSTRAINTS;
DROP TABLE Credit_Card CASCADE CONSTRAINTS;
DROP TABLE Profiles CASCADE CONSTRAINTS;
DROP TABLE Genre CASCADE CONSTRAINTS;
DROP TABLE View_ CASCADE CONSTRAINTS;
DROP TABLE Casting CASCADE CONSTRAINTS;
--DROP TRIGGER ratingUpdate;
--DROP TRIGGER profileConstrain;
--DROP TRIGGER creditCardConstrain;

CREATE TABLE Movie
(movie_id CHAR(6),
movie_name VARCHAR(50),
Producer VARCHAR(50),
average_rating FLOAT CHECK(average_rating < 6 AND average_rating >= 1),-- restrict 1-5
production_year INTEGER,
PRIMARY KEY(movie_id)  
);


INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00000','Titanic','Paramount Pictures', null, 1997);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00001','Jumanji: The Next Level','Columbia Pictures', null, 2019);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00002','Passengers', 'Columbia Pictures', null, 2016);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00003','Gravity','Heyday Films',null,2013);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00004','Buried','The Safran Company',null,2010);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00005','Cast Away','ImageMovers',null,2000);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00006','127 Hours','Everest Entertainment',null,2010);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00007','Gerry','Dany Wolf',null,2002);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00008','I Am Legend','Village Roadshow Pictures',null,2007);
INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year)
VALUES('M00009','Holidate','Wonderland Sound and Vision',null,2020);
 
CREATE TABLE Member_
(member_id CHAR(6),
first_name VARCHAR(30),
last_name VARCHAR(30),
PRIMARY KEY(member_id)
);

INSERT INTO Member_(member_id, first_name, last_name) 
VALUES('M00000','Erik','Garcia');
INSERT INTO Member_(member_id, first_name, last_name) 
VALUES('M00001','Mary','Smith');
INSERT INTO Member_(member_id, first_name, last_name) 
VALUES('M00002','Patricia','Johnson');
INSERT INTO Member_(member_id, first_name, last_name) 
VALUES('M00003','jennifer','Miller.');
INSERT INTO Member_(member_id, first_name, last_name) 
VALUES('M00004','David','Jones');

CREATE TABLE Actor
(actor_id CHAR(6),
first_name VARCHAR(50),
last_name VARCHAR(50),
PRIMARY KEY(actor_id)
);

INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00000','Leonardo','DiCaprio');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00001','Kate','Winslet');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00002','Billy','Zane');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00003','Kathy','Bates');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00004','Dwayne','Johnson');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00005','Jack','Black');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00006','Kevin','Hart');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00007','Karen','Gillan');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00008','Nick','Jonas');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00009','Awkwafina','');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00010','Jennifer','Lawrence');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00011','Chris','Pratt');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00012','Michael','Sheen');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00013','Sandra','Bullock');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00014','George','Clooney');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00015','Ryan','Reynolds');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00016','Tom','Hanks');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00017','Helen','Hunt');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00018','James','Franco');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00019','Amber','Tamblyn');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00020','Matt','Damon');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00021','Casey','Affleck');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00022','Will','Smith');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00023','Alice','Braga');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00024','Dash','Mihok');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00025','Emma','Roberts');
INSERT INTO Actor(actor_id, first_name ,last_name)
VALUES('A00026','Luke','Bracey');

CREATE TABLE Credit_Card
(member_id CHAR(6),
number_ VARCHAR(16),
exp_date DATE,
security_code CHAR(4),
PRIMARY KEY(member_id, number_),
FOREIGN KEY(member_id)REFERENCES Member_ ON DELETE CASCADE
);


CREATE TRIGGER creditCardConstrain
BEFORE INSERT ON Credit_Card
FOR EACH ROW
DECLARE
creditCardNum  INTEGER;
Too_many  EXCEPTION;

BEGIN
SELECT COUNT(*) INTO creditCardNum
FROM Credit_Card
WHERE member_id = :NEW.member_id;

IF creditCardNum > 2 THEN
    RAISE Too_many;
END IF;

EXCEPTION 
    WHEN Too_many THEN
        RAISE_APPLICATION_ERROR(-20000, 'You can have up to 3 credit cards on file!');
END creditCardConstrain;
/


INSERT INTO Credit_Card(member_id, number_, exp_date, security_code)
VALUES ('M00000', '1234567891234567','01-may-2022' ,'123');
INSERT INTO Credit_Card(member_id, number_, exp_date, security_code)
VALUES ('M00001', '1111222233334444','01-may-2023' ,'111');

CREATE TABLE Profiles
(member_id CHAR(6),
profile_name VARCHAR(50),
favorite_movie_genre VARCHAR(50),
PRIMARY KEY(member_id, profile_name),
FOREIGN KEY(member_id)REFERENCES member_ ON DELETE CASCADE
);

CREATE TRIGGER profileConstrain
BEFORE INSERT ON  Profiles
FOR EACH ROW
DECLARE
profileNum  INTEGER;
Too_many  EXCEPTION;

BEGIN
SELECT COUNT(*) INTO profileNum
FROM Profiles
WHERE member_id = :NEW.member_id;

IF profilenum > 4 THEN
    RAISE Too_many;
END IF;

EXCEPTION 
    WHEN Too_many THEN
        RAISE_APPLICATION_ERROR(-20000, 'You can have up to 5 profiles!');
END profileConstrain;
/

INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00000','Erik','Adventure');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00000','Emily','Romance');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00000','Sophia','Comedy');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00000','Mia','Romance');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00000','Alex','Sci-fi');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00001','Mary','Horror');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00001','Kate','Comedy');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00002','Paty','Drama');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00002','Sam','Action');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00003','Jenny','Romance');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00003','Emma','Drama');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00004','David','Comedy');
INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre)
VALUES('M00004','Carol','Sci-fi');

CREATE TABLE Genre
(movie_id CHAR(6),
genre_name VARCHAR(50),
PRIMARY KEY(movie_id, genre_name),
FOREIGN KEY(movie_id)REFERENCES Movie ON DELETE CASCADE
);

INSERT INTO Genre(movie_id, genre_name)
VALUES('M00000','Romance');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00000','Drama');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00001','Fantasy');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00001','Adventure');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00002','Sci-fi');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00002','Romance');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00003','Sci-fi');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00003','Thriller');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00004','Thriller');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00004','Drama');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00005','Adventure');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00005','Drama');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00006','Drama');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00006','Survival');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00007','Indie film');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00007','Drama');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00008','Horror');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00008','Sci-fi');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00009','Romance');
INSERT INTO Genre(movie_id, genre_name)
VALUES('M00009','Comedy');

CREATE TABLE View_
(movie_id CHAR(6),
member_id CHAR(6),
profile_name VARCHAR(50),
rating FLOAT CHECK(rating < 6 AND rating >= 1),
PRIMARY KEY(movie_id, member_id, profile_name),
FOREIGN KEY(movie_id) REFERENCES Movie,
FOREIGN KEY (member_id, profile_name) REFERENCES Profiles
);

CREATE OR REPLACE TRIGGER ratingUpdate
FOR INSERT ON  View_ COMPOUND TRIGGER

newRating  FLOAT;
m_id      CHAR(6);

BEFORE EACH ROW IS
BEGIN
m_id := :NEW.movie_id;
END BEFORE EACH ROW;

AFTER STATEMENT IS
BEGIN

SELECT AVG(rating)INTO newRating 
FROM View_ 
WHERE movie_id = m_id;

UPDATE Movie
SET average_rating = newRating 
WHERE movie_id = m_id;
END AFTER STATEMENT;

END ratingUpdate;
/


INSERT INTO View_(movie_id, member_id, profile_name, rating)
VALUES('M00000', 'M00000','Erik',4.1);
INSERT INTO View_(movie_id, member_id, profile_name, rating)
VALUES('M00001', 'M00000','Erik',4.3);
INSERT INTO View_(movie_id, member_id, profile_name, rating)
VALUES('M00002', 'M00001','Kate',4.5);
INSERT INTO View_(movie_id, member_id, profile_name, rating)
VALUES('M00002', 'M00004','Carol',4.0);


CREATE TABLE Casting
(movie_id CHAR(6),
actor_id CHAR(6),
PRIMARY KEY(movie_id, actor_id),
FOREIGN KEY(movie_id) REFERENCES Movie,
FOREIGN KEY (actor_id) REFERENCES Actor
);

INSERT INTO Casting (movie_id, actor_id)
VALUES('M00000' , 'A00000');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00000' , 'A00001');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00000' , 'A00002');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00000' , 'A00003');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00004');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00005');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00006');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00007');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00008');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00001' , 'A00009');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00002' , 'A00010');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00002' , 'A00011');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00002' , 'A00012');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00003' , 'A00013');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00003' , 'A00014');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00004' , 'A00015');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00005' , 'A00016');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00005' , 'A00017');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00006' , 'A00018');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00006' , 'A00019');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00007' , 'A00020');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00007' , 'A00021');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00008' , 'A00022');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00008' , 'A00023');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00008' , 'A00024');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00009' , 'A00025');
INSERT INTO Casting (movie_id, actor_id)
VALUES('M00009' , 'A00026');


SELECT * FROM Movie;
SELECT * FROM Member_;
SELECT * FROM Actor;
SELECT * FROM Credit_Card;
SELECT * FROM Profiles;
SELECT * FROM Genre;
SELECT * FROM View_;
SELECT * FROM Casting;





