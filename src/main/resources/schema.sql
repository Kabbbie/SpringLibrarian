DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;
create table users(
username varchar(36) primary key,
password varchar(50) not null
);

create table books(
isbn varchar(15) primary key,
author varchar(30) not null,
title varchar(50) not null,
username varchar(36),
foreign key (username) references users(username)
);

