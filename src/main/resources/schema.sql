CREATE TABLE users (
    id int not null auto_increment,
    name varchar(255) not null,
    password varchar(255) not null,
    city varchar(255) not null,
    birthday date not null,
    role int not null,
    primary key (id)
);