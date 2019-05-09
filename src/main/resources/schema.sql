DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS books;
create table users(
username varchar(36) primary key,
password varchar(128) not null,
enabled boolean not null
);

create table books(
isbn varchar(15) primary key,
author varchar(30) not null,
title varchar(50) not null,
username varchar(36)
);
create table authorities (
	username varchar(36) not null,
	authority varchar(36) not null
);

