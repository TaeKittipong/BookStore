DROP TABLE IF EXISTS user;

CREATE TABLE user (
  username varchar(50)  PRIMARY KEY,
  password VARCHAR(100) NOT NULL,
  name VARCHAR(250) ,
  surname VARCHAR(250) ,
  date_of_birth VARCHAR(15)
);


DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
  username varchar(50) NOT NULL,
  book_id BIGINT NOT NULL
  );